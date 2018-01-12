package bean;

import java.sql.SQLException;
import java.util.ArrayList;

import model.UtenteDAO;

public class Utente {

    public Utente() {}
    
    public Utente(String email, String nome, String cognome, String password, String via, String civico, String cap, String citta) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.citta = citta;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setVia(String via) {
		this.via = via;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getVia() {
		return via;
	}

	public String getCivico() {
		return civico;
	}

	public String getCap() {
		return cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
  

    public ArrayList<CartaDiCredito> getCarte() {
        return carte;
    }

    public void setCarte(ArrayList<CartaDiCredito> carte) {
        this.carte = carte;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public ArrayList<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(ArrayList<Ordine> ordini) {
        this.ordini = ordini;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
    
    public static Utente controllaCredenziali(String email, String password) throws SQLException {
        return UtenteDAO.Check(email, password);
    }
    
    public static void aggiornaIndirizzo(String via,String numeroCivico, String cap, String citta, String utente) throws SQLException{
    	UtenteDAO.doUpdateAddress( via, numeroCivico,  cap,  citta,  utente);
    }
    
    public static boolean checkEmail(String email) throws SQLException {
    	return UtenteDAO.checkEmail(email);
    }
    
    public void aggiornaPassword(String password) throws SQLException {
        UtenteDAO.doUpdatePassword(password, this);
    }
    
    public void aggiornaEmail(String email) throws SQLException {
    	UtenteDAO.doUpdateEmail(email, this);
    }
    public void eliminaUtente(String email) throws SQLException {
    	UtenteDAO.doDeleteUser(email);
    }
    public Utente getPass(String user) throws SQLException{
    	return UtenteDAO.doRetrieveByKey(user);
    	
    }

    private String email = null, password= null, nome= null, cognome= null, stato = "unlogged", via = null, civico = null, cap = null, citta=null;

    private ArrayList<CartaDiCredito> carte = null;
    private Carrello carrello = new Carrello();
    private ArrayList<Ordine> ordini = null;
}
