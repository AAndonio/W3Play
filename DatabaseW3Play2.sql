-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema progettoweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema progettoweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `progettoweb` DEFAULT CHARACTER SET utf8 ;
USE `progettoweb` ;

-- -----------------------------------------------------
-- Table `progettoweb`.`Utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Utente` (
  `Email` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Via` VARCHAR(45) NOT NULL,
  `Civico` VARCHAR(4) NOT NULL,
  `CAP` VARCHAR(6) NOT NULL,
  `Citta` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Email`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`CartaDiCredito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`CartaDiCredito` (
  `NumeroCarta` VARCHAR(20) NOT NULL,
  `Titolare` VARCHAR(45) NOT NULL,
  `Scadenza` DATE NOT NULL,
  `CCV` INT NOT NULL,
  PRIMARY KEY (`NumeroCarta`),
  UNIQUE INDEX `NumeroCarta_UNIQUE` (`NumeroCarta` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`Associare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Associare` (
  `Utente` VARCHAR(45) NOT NULL,
  `Carta` VARCHAR(20) NOT NULL,
  INDEX `fk_Associare_1_idx` (`Utente` ASC),
  INDEX `fk_Associare_2_idx` (`Carta` ASC),
  CONSTRAINT `fk_Associare_1`
    FOREIGN KEY (`Utente`)
    REFERENCES `progettoweb`.`Utente` (`Email`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Associare_2`
    FOREIGN KEY (`Carta`)
    REFERENCES `progettoweb`.`CartaDiCredito` (`NumeroCarta`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`Ordine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Ordine` (
  `idOrdine` INT NOT NULL AUTO_INCREMENT,
  `Prezzo` VARCHAR(45) NOT NULL,
  `DataAcquisto` DATETIME NOT NULL,
  `Carta` VARCHAR(20) NOT NULL,
  `Utente` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idOrdine`),
  UNIQUE INDEX `idOrdine_UNIQUE` (`idOrdine` ASC),
  INDEX `fk_Ordine_1_idx` (`Utente` ASC),
  CONSTRAINT `fk_Ordine_1`
    FOREIGN KEY (`Utente`)
    REFERENCES `progettoweb`.`Utente` (`Email`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`Carrello`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Carrello` (
  `idCarrello` INT NOT NULL AUTO_INCREMENT,
  `QtaProdotti` INT NOT NULL,
  `PrezzoTotale` FLOAT NOT NULL,
  `Utente` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCarrello`),
  INDEX `fk_Carrello_1_idx` (`Utente` ASC),
  UNIQUE INDEX `idCarrello_UNIQUE` (`idCarrello` ASC),
  CONSTRAINT `fk_Carrello_1`
    FOREIGN KEY (`Utente`)
    REFERENCES `progettoweb`.`Utente` (`Email`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`Prodotto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Prodotto` (
  `idProdotto` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Produttore` VARCHAR(45) NOT NULL,
  `Piattaforma` VARCHAR(45) NULL,
  `Genere` VARCHAR(45) NULL,
  `Descrizione` TEXT NULL,
  `Immagini` TEXT NOT NULL,
  `Prezzo` FLOAT NOT NULL,
  `Disponibilita` INT NOT NULL,
  `DataUscita` DATE NOT NULL,
  `numVenduti` INT NOT NULL,
  `linkVideo` VARCHAR(150) NULL,
  PRIMARY KEY (`idProdotto`),
  UNIQUE INDEX `idProdotto_UNIQUE` (`idProdotto` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`Inserire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Inserire` (
  `Carrello` INT NOT NULL,
  `Prodotto` INT NOT NULL,
  `Quantita` INT NOT NULL,
  INDEX `fk_Inserire_1_idx` (`Carrello` ASC),
  INDEX `fk_Inserire_2_idx` (`Prodotto` ASC),
  CONSTRAINT `fk_Inserire_1`
    FOREIGN KEY (`Carrello`)
    REFERENCES `progettoweb`.`Carrello` (`idCarrello`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inserire_2`
    FOREIGN KEY (`Prodotto`)
    REFERENCES `progettoweb`.`Prodotto` (`idProdotto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`DettagliOrdine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`DettagliOrdine` (
  `Prodotto` INT NOT NULL,
  `Ordine` INT NOT NULL,
  `Quantita` INT NOT NULL,
  INDEX `fk_DettagliOrdine_1_idx` (`Ordine` ASC),
  INDEX `fk_DettagliOrdine_2_idx` (`Prodotto` ASC),
  CONSTRAINT `fk_DettagliOrdine_1`
    FOREIGN KEY (`Ordine`)
    REFERENCES `progettoweb`.`Ordine` (`idOrdine`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DettagliOrdine_2`
    FOREIGN KEY (`Prodotto`)
    REFERENCES `progettoweb`.`Prodotto` (`idProdotto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `progettoweb`.`amministratore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`amministratore` (
  `Email` VARCHAR(45) NOT NULL,
  `Ruolo` varchar(10) not null,
  PRIMARY KEY (`Email`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `progettoweb`.`Processare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `progettoweb`.`Processare` (
  `Ordine` INT NOT NULL,
  `Prodotto` INT NOT NULL,
  `Quantita` INT NOT NULL,
  INDEX `fk_Processare_1_idx` (`Ordine` ASC),
  INDEX `fk_Processare_2_idx` (`Prodotto` ASC),
  CONSTRAINT `fk_Processare_1`
    FOREIGN KEY (`Ordine`)
    REFERENCES `progettoweb`.`Ordine` (`idOrdine`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Processare_2`
    FOREIGN KEY (`Prodotto`)
    REFERENCES `progettoweb`.`Prodotto` (`idProdotto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
