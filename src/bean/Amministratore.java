package bean;

import java.sql.SQLException;

import model.AmministratoreDAO;

/**
 * Classe per l'Amministratore del sistema
 * @author Alfonso
 */
public class Amministratore {
	
	public Amministratore() {}
	
	public Amministratore(Utente utente, Ruolo ruolo) {
		this.ruolo  = ruolo;
		this.utente = utente;
	}
		
	/**
	 * Restituisce l'email dell'account utente associato all'amministratore.
	 * @return String: email utente
	 */
	public String getEmail() {
		
		return (utente != null ? utente.getEmail() : null);
	}

	/**
	 * Setta l'email dell'account utente associato all'amministratore.
	 * @param email: email da cambiare
	 */
	public void setEmail(String email) {
		
		if (utente != null)
			utente.setEmail(email);
		else
			System.out.println("Amministratore.setEmail: utente null!");
	}
	
	/**
	 * Ritorna la password dell'account utente associato all'amministratore.
	 * @return String: passowrd utente
	 */
	public String getPassword() {
		
		return (utente != null ? utente.getPassword() : null);
	}
	
	/**
	 * Cambia la password dell'account utente associato all'amministratore.
	 * @param password: nuova password
	 */
	public void setPassword(String password) {
		
		if (utente != null)
			utente.setPassword(password);
		else
			System.out.println("Amministratore.setPassword: utente null!");
	}
	
	/**
	 * Setta il ruolo all'amministratore, i ruoli disponibili sono {Backoffice, Business}
	 * @param ruolo: enum definito nella classe Amministratore
	 */
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
	/**
	 * Ritorna il ruolo corrente dell'amministratore.
	 * @return Ruolo: enumeratore corrispondete al ruolo corrente.
	 */
	public Ruolo getRuolo() {
		return ruolo;
	}
	
	/**
	 * Ritorna lo stato corrente dell'amministratore, gli stati consentiti sono: {loggato, unlogged, errore}	
	 * @return String: stato attuale
	 */
	public String getStato() {
		return stato;
	}
	
	/**
	 * Controlla se l'amministratore è attualmente loggato.
	 * @return boolean: true se è loggato, false altrimenti
	 */
	public boolean isLoggato() {
		
		return Utente.LOGGATO.equals(stato);
	}
	
	/**
	 * Controlla se l'aministratore non è loggato.
	 * @return boolean: true se è non loggato, false altrimenti
	 */
	public boolean isNotLoggato() {
    	
    	return Utente.UNLOGGED.equals(stato);
    }
	
	/**
	 * Setta lo stato dell'amministratore
	 * @param stato: {loggato, unlogged, errore}
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	/**
	 * Ritorna un riferimento all'account utente corrispondente all'amministratore
	 * @return Utente
	 */
	public Utente getUtente() {
		return utente;
	}
	
	/**
	 * Setta l'utente corrispondete all'amministratore
	 * @param utente: instanza di Utente
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	/**
	 * Controlla, nel database, che le credenziali inserite corrispondino ad un account amministratore.
	 * @param email: email amministatore
	 * @param pass: password dell'account utente corrispondente all'amministratore
	 * @return Amministratore: ritorna un instanza di amministratore.
	 * @throws SQLException
	 */
	public static Amministratore controllaCredenziali(String email, String pass) throws SQLException {
		return AmministratoreDAO.controllaCredenziali(email, pass);
	}
	
	/**
	 * Modifica, nel database, la password associata all'account utente di amministratore
	 * @param password: nuova password 
	 * @throws SQLException
	 */
	public void aggiornaPassword(String password) throws SQLException {
		AmministratoreDAO.doUpdatePassword(password, this);
	}
	
	/**
	 * Elimina l'account amministratore corrente dal database
	 * @param email: email che identifica l'account
	 * @throws SQLException
	 */
	public void eliminaAdmin(String email) throws SQLException {
	    AmministratoreDAO.doDeleteAdmin(email);
	}
		
	/* (non-Javadoc) @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return "Amministratore [stato=" + stato + ", ruolo=" + ruolo + ", utente=" + utente + "]";
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