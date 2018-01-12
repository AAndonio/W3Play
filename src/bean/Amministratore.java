package bean;

import java.sql.SQLException;

import model.AmministratoreDAO;
import model.UtenteDAO;

public class Amministratore {
	
	public Amministratore() {}
	
	public Amministratore(Utente utente, Ruolo ruolo) {
		this.ruolo  = ruolo;
		this.utente = utente;
	}
		
	public String getEmail() {
		
		return (utente != null ? utente.getEmail() : null);
	}

	public void setEmail(String email) {
		
		if (utente != null)
			utente.setEmail(email);
		else
			System.out.println("Amministratore.setEmail: utente null!");
	}

	public String getPassword() {
		
		return (utente != null ? utente.getPassword() : null);
	}

	public void setPassword(String password) {
		
		if (utente != null)
			utente.setPassword(password);
		else
			System.out.println("Amministratore.setPassword: utente null!");
	}
	
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
	public Ruolo getRuolo() {
		return ruolo;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public Utente getUtente() {
		return utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public static Amministratore controllaCredenziali(String email, String pass) throws SQLException {
		return AmministratoreDAO.controllaCredenziali(email, pass);
	}
	
	public void aggiornaPassword(String password) throws SQLException {
		AmministratoreDAO.doUpdatePassword(password, this);
	}
	 public void eliminaAdmin(String email) throws SQLException {
	    	AmministratoreDAO.doDeleteAdmin(email);
	    }
	 
	//-- fields
	private String stato = "unlogged";
	private Ruolo ruolo;
	private Utente utente;
	
	//-- ruoli amministratore 
	public enum Ruolo {
		Backoffice,
		Business
	}
}