CREATE DATABASE  IF NOT EXISTS `progettoweb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `progettoweb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: progettoweb
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `amministratore`
--

DROP TABLE IF EXISTS `amministratore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amministratore` (
  `Email` varchar(45) NOT NULL,
  `Ruolo` varchar(10) NOT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amministratore`
--

LOCK TABLES `amministratore` WRITE;
/*!40000 ALTER TABLE `amministratore` DISABLE KEYS */;
INSERT INTO `amministratore` VALUES ('augu96@hotmail.it','Business'),('ndonio96@gmail.com','Backoffice');
/*!40000 ALTER TABLE `amministratore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `associare`
--

DROP TABLE IF EXISTS `associare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `associare` (
  `Utente` varchar(45) NOT NULL,
  `Carta` varchar(20) NOT NULL,
  KEY `fk_Associare_1_idx` (`Utente`),
  KEY `fk_Associare_2_idx` (`Carta`),
  CONSTRAINT `fk_Associare_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`Email`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Associare_2` FOREIGN KEY (`Carta`) REFERENCES `cartadicredito` (`NumeroCarta`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `associare`
--

LOCK TABLES `associare` WRITE;
/*!40000 ALTER TABLE `associare` DISABLE KEYS */;
INSERT INTO `associare` VALUES ('peppe.dello.stretto97@gmail.com','1111 2222 3333 4444'),('ndonio96@gmail.com','1234 2222 3333 4444'),('ggarreffa@gmail.com','1111 5678 3333 4444'),('luca@mail.com','6666 6666 6666 6666'),('augu96@hotmail.it','1234 5678 9012 1946');
/*!40000 ALTER TABLE `associare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrello`
--

DROP TABLE IF EXISTS `carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrello` (
  `idCarrello` int(11) NOT NULL AUTO_INCREMENT,
  `QtaProdotti` int(11) NOT NULL,
  `PrezzoTotale` float NOT NULL,
  `Utente` varchar(45) NOT NULL,
  PRIMARY KEY (`idCarrello`),
  UNIQUE KEY `idCarrello_UNIQUE` (`idCarrello`),
  KEY `fk_Carrello_1_idx` (`Utente`),
  CONSTRAINT `fk_Carrello_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`Email`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrello`
--

LOCK TABLES `carrello` WRITE;
/*!40000 ALTER TABLE `carrello` DISABLE KEYS */;
INSERT INTO `carrello` VALUES (1,3,858.96,'peppe.dello.stretto97@gmail.com'),(2,0,0,'ndonio96@gmail.com'),(3,0,0,'ggarreffa@gmail.com'),(4,0,0,'gg@gmail.com'),(26,2,239.98,'luca@mail.com'),(27,0,0,'augu96@hotmail.it');
/*!40000 ALTER TABLE `carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartadicredito`
--

DROP TABLE IF EXISTS `cartadicredito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cartadicredito` (
  `NumeroCarta` varchar(20) NOT NULL,
  `Titolare` varchar(45) NOT NULL,
  `Scadenza` date NOT NULL,
  `CCV` int(11) NOT NULL,
  PRIMARY KEY (`NumeroCarta`),
  UNIQUE KEY `NumeroCarta_UNIQUE` (`NumeroCarta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartadicredito`
--

LOCK TABLES `cartadicredito` WRITE;
/*!40000 ALTER TABLE `cartadicredito` DISABLE KEYS */;
INSERT INTO `cartadicredito` VALUES ('1111 2222 3333 4444','Giuseppe Dello Stretto','2020-06-20',123),('1111 2222 3333 5678','Daniele Palmieri','2022-11-12',345),('1111 5678 3333 4444','Giuseppe Garreffa','2019-07-22',789),('1234 1234 4321 4321','Peppe','2018-06-06',455),('1234 2222 3333 4444','Antonio Vivone','2025-04-15',456),('1234 5678 9012 1946','Augusto','2020-01-01',150),('1234 5678 9012 3456','Alfonso Di Pace','2027-02-21',678),('6666 6666 6666 6666','luca anzalone','2020-10-10',555);
/*!40000 ALTER TABLE `cartadicredito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dettagliordine`
--

DROP TABLE IF EXISTS `dettagliordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dettagliordine` (
  `prodotto` int(11) NOT NULL,
  `ordine` int(11) NOT NULL,
  `quantita` int(11) DEFAULT '1',
  UNIQUE KEY `prodotto` (`prodotto`,`ordine`),
  KEY `ordine` (`ordine`),
  CONSTRAINT `dettagliordine_ibfk_1` FOREIGN KEY (`prodotto`) REFERENCES `prodotto` (`idProdotto`),
  CONSTRAINT `dettagliordine_ibfk_2` FOREIGN KEY (`ordine`) REFERENCES `ordine` (`idOrdine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dettagliordine`
--

LOCK TABLES `dettagliordine` WRITE;
/*!40000 ALTER TABLE `dettagliordine` DISABLE KEYS */;
INSERT INTO `dettagliordine` VALUES (3,23,1),(6,22,1),(9,11,1),(9,21,1),(10,22,1),(15,21,1),(17,21,1),(20,20,1),(22,17,1);
/*!40000 ALTER TABLE `dettagliordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inserire`
--

DROP TABLE IF EXISTS `inserire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inserire` (
  `Carrello` int(11) NOT NULL,
  `Prodotto` int(11) NOT NULL,
  `Quantita` int(11) NOT NULL,
  KEY `fk_Inserire_1_idx` (`Carrello`),
  KEY `fk_Inserire_2_idx` (`Prodotto`),
  CONSTRAINT `fk_Inserire_1` FOREIGN KEY (`Carrello`) REFERENCES `carrello` (`idCarrello`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inserire_2` FOREIGN KEY (`Prodotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inserire`
--

LOCK TABLES `inserire` WRITE;
/*!40000 ALTER TABLE `inserire` DISABLE KEYS */;
INSERT INTO `inserire` VALUES (1,17,2),(1,19,1),(3,10,2),(26,19,1),(26,11,1);
/*!40000 ALTER TABLE `inserire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordine` (
  `idOrdine` int(11) NOT NULL AUTO_INCREMENT,
  `Prezzo` varchar(45) NOT NULL,
  `DataAcquisto` datetime NOT NULL,
  `Carta` varchar(20) NOT NULL,
  `Utente` varchar(45) NOT NULL,
  PRIMARY KEY (`idOrdine`),
  UNIQUE KEY `idOrdine_UNIQUE` (`idOrdine`),
  KEY `fk_Ordine_1_idx` (`Utente`),
  CONSTRAINT `fk_Ordine_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`Email`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` VALUES (11,'50.99','2018-01-24 16:14:41','555','luca@mail.com'),(17,'34.98','2018-01-30 13:19:29','6666 6666 6666 6666','luca@mail.com'),(20,'30.98','2018-02-02 12:48:43','1234 2222 3333 4444','ndonio96@gmail.com'),(21,'590.97','2018-02-04 18:45:52','1234 2222 3333 4444','ndonio96@gmail.com'),(22,'62.95','2018-02-04 18:54:51','1234 5678 9012 1946','augu96@hotmail.it'),(23,'19.9','2018-02-04 18:55:03','1234 5678 9012 1946','augu96@hotmail.it');
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processare`
--

DROP TABLE IF EXISTS `processare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processare` (
  `Ordine` int(11) NOT NULL,
  `Prodotto` int(11) NOT NULL,
  `Quantita` int(11) NOT NULL,
  KEY `fk_Processare_1_idx` (`Ordine`),
  KEY `fk_Processare_2_idx` (`Prodotto`),
  CONSTRAINT `fk_Processare_1` FOREIGN KEY (`Ordine`) REFERENCES `ordine` (`idOrdine`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Processare_2` FOREIGN KEY (`Prodotto`) REFERENCES `prodotto` (`idProdotto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processare`
--

LOCK TABLES `processare` WRITE;
/*!40000 ALTER TABLE `processare` DISABLE KEYS */;
/*!40000 ALTER TABLE `processare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotto` (
  `idProdotto` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `Produttore` varchar(45) NOT NULL,
  `Piattaforma` varchar(45) DEFAULT NULL,
  `Genere` varchar(45) DEFAULT NULL,
  `Descrizione` text,
  `Immagini` text NOT NULL,
  `Prezzo` float NOT NULL,
  `Disponibilita` int(11) NOT NULL,
  `DataUscita` date NOT NULL,
  `numVenduti` int(11) NOT NULL,
  `linkVideo` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idProdotto`),
  UNIQUE KEY `idProdotto_UNIQUE` (`idProdotto`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,'Uncharted: Leredità perduta','Sony Interactive Entertainment','PS4','Action/Adventure','descrizione uncharted','copertine/uncharted.jpg',40.98,15,'2017-08-29',4,'https://www.youtube.com/embed/DZaxBHmXpIo'),(2,'Crash bandicoot N. Sane Trilogy','Activision','PS4','Action/Adventure','descrizione crash','copertine/Crash bandicootPS4.jpg',40.78,18,'2017-06-30',1,'https://www.youtube.com/embed/4Boamnaj2Y8'),(3,'Grand Theft Auto IV','Rockstar Games','PS3','Action/adventure','descrizione gtaIV','copertine/GrandTheftAutoIV.jpg',19.9,23,'2012-08-29',1,'https://www.youtube.com/embed/oAAFbj6teIo'),(4,'Persona 5','Deep Silver','PS3','RPG','descrizione persona 5','copertine/Persona5PS3.jpg',44.98,13,'2017-07-20',1,'https://www.youtube.com/embed/I5dh-csRaeE'),(5,'Odin Sphere','Atlus','PSVita','Action/adventure','descrizione odin sphere','copertine/OdinSpherePSVita.jpg',27.98,30,'2017-02-02',4,'https://www.youtube.com/embed/QypWX5eaM9I'),(6,'One Piece: Burning Blood','Bandai Namco Games','PSVita','Espansione','descrizione one piece','copertine/OnePieceBurningBloodPSVita.jpg',14.99,99,'2017-05-29',1,'https://www.youtube.com/embed/DDg7yNByxWs'),(7,'Resident Evil Revelation','Capcom','XBOX ONE','Action/adventure','descrizione RE','copertine/Revelations.jpg',27.98,79,'2010-11-29',1,'https://www.youtube.com/embed/pgSF2aY9MtM'),(8,'NBA 2K18','2K','XBOX 360','Sport','descrizione nba','copertine/NBA2K18.jpg',60.78,17,'2015-09-17',1,'https://www.youtube.com/embed/dQEOSCLnPL8'),(9,'LEGO Worlds','Warner Bros. Interactive','Nintendo Switch','Action/Adventure','Descrizione lego','copertine/LEGOWorldsSwitch.jpg',50.99,51,'2016-12-29',1,'https://www.youtube.com/embed/frRfo4sKxtk'),(10,'Bomberman 64','Nintendo','Nintendo WiiU','Gioco','descrizione bomberman','copertine/bomberman.jpg',10.99,21,'2017-01-29',1,'https://www.youtube.com/embed/LEJS2d7NJm4'),(11,'Monster hunter Stories','Capcom','Nintendo 3DS','RPG','descrizione monster','copertine/MonsterHunterStories3DS.jpg',40.98,10,'2017-03-05',21,'https://www.youtube.com/embed/r46Jbq-ZNok'),(12,'PS4 Slim 500GB + PSN Card 10','Sony Interactive Entertainment','','console','descrizione ps4','copertine/PS4 Slim 500GB.jpg',299,17,'2017-07-19',1,''),(13,'PS3 Ultra Slim 500GB Nera','Sony Interactive Entertainment','','console','descrizione ps3','copertine/PS3 Ultra Slim 500GB Nera.jpg',199,7,'2011-02-10',8,''),(14,'PSVita Wi-Fi','Sony Interactive Entertainment','','console','descrizione psvita','copertine/PSVita Wi-Fi.jpg',199.98,9,'2016-08-25',1,''),(15,'Xbox One S Bianca 1TB','Microsoft','','console','descrizione xbox','copertine/Xbox 360 250Gb.jpg',259.99,51,'2016-08-29',1,''),(16,'Xbox 360 250Gb','Microsoft','','console','descrizione xbox','copertine/Xbox 360 250Gb.jpg',79.99,9,'2017-05-19',1,''),(17,'Nintendo Switch','Nintendo','Nintendo','console','Console portatile nintendo','copertine/Nintendo Switch.jpg',279.99,28,'2014-08-20',1,''),(18,'Nintendo Wii U Bianca','Nintendo','','console','descrizione wiiu','copertine/Nintendo Wii U Bianca.jpeg',149.99,7,'2014-03-29',1,''),(19,'New Nintendo 3DS XL Nero/Arancione','Nintendo','','console','descrizione 3ds','copertine/New Nintendo 3DS XL Nero_Arancione.jpg',199,2,'2012-12-29',12,''),(20,'Dishonored: La morte dell Esterno ','Bethesda Softworks','PC','Action/adventure','descrizione dishonored','copertine/dishonored.jpg',30.98,12,'2015-11-29',1,'https://www.youtube.com/embed/Rm4s8UAsC3A'),(21,'Fallout 4 - Game Of The Year Edition','Bethesda Softworks','PC','Action/adventure','descrizione fallout','copertine/fallout.jpg',40.98,17,'2010-10-20',1,'https://www.youtube.com/embed/Rs1CrdSuA6k'),(22,'World Of Warcraft: Legion','Blizzard','PC','RPG','descrizione wow','copertine/legion.jpg',34.98,78,'2012-10-10',1,'https://www.youtube.com/embed/lccuX8UQssQ'),(26,'Dark Souls3','From Software','Xbox One','GDR','Dark Souls 3 Game of the Year Edition','copertine/DSIII_FFE_BoxFront_2D_XONE_BR.jpg',49.99,50,'2016-04-26',0,'');
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `Email` varchar(45) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Via` varchar(45) NOT NULL,
  `Civico` varchar(4) NOT NULL,
  `CAP` varchar(6) NOT NULL,
  `Citta` varchar(45) NOT NULL,
  PRIMARY KEY (`Email`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('augu96@hotmail.it','Nome','Cognome','augusto','via','12','84090','city'),('gg@gmail.com','Giuseppe','Garreffa','giuseppe','Via Napoli','12','81020','Caserta'),('ggarreffa@gmail.com','Giuseppe','Garreffa','giuseppeg','Via Napoli','12','81020','Caserta'),('luca@mail.com','luca','anzalone','pascal','viale','1212','5556','salerno'),('ndonio96@gmail.com','Antonio','Vivone','antonio','Via Anguria','45','84022','Campagna'),('peppe.dello.stretto97@gmail.com','Giuseppe','Dello Stretto','giuseppe','Via Sacco e Vanzetti','9','81020','Caserta');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'progettoweb'
--

--
-- Dumping routines for database 'progettoweb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-04 18:59:21
