package bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.UtenteDAO;

/**
 * Utente
 * @author Antonio
 */
public class Utente {

    public Utente() { setCarrello(new Carrello()); }
    
    public Utente(String email, String nome, String cognome, String password, String via, String civico, String cap, String citta) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.citta = citta;
        
        setCarrello(new Carrello());
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
    
    public void addCarta(CartaDiCredito carta) {
    	this.carte.add(carta);
    }
    
    public void setCarte(ArrayList<CartaDiCredito> carte) {
        this.carte = carte;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
        this.carrello.setUtente(this);
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(List<Ordine> ordini) {
        this.ordini = ordini;
    }

    public String getStato() {
        return stato;
    }

    public boolean isLoggato() {
    	
    	return LOGGATO.equals(stato);
    }
    
    public boolean isNotLoggato() {
    	
    	return UNLOGGED.equals(stato);
    }
    
    public void setStato(String stato) {
        this.stato = stato;
    }
    
    /**
     * Controlla che le credenziali inserite corrispondano ad un account utente
     * @param email: email utente
     * @param password: password utente
     * @return Utente: account utente trovato
     * @throws SQLException
     */
    public static Utente controllaCredenziali(String email, String password) throws SQLException {
        return UtenteDAO.Check(email, password);
    }
    
    /**
     * Aggiorna l'indirizzo dell'utente
     * @param via: nuova via
     * @param numeroCivico: nuovo numero civico
     * @param cap: nuovo cap
     * @param citta: nuova città
     * @param utente: utente di cui aggiornare l'indirizzo
     * @throws SQLException
     */
    public static void aggiornaIndirizzo(String via,String numeroCivico, String cap, String citta, String utente) throws SQLException{
    	UtenteDAO.doUpdateAddress( via, numeroCivico,  cap,  citta,  utente);
    }
    
    /**
     * @param email: email di un account utente
     * @return boolean: true se l'email corrisponde ad un account utente, false altrimenti
     * @throws SQLException
     */
    public static boolean checkEmail(String email) throws SQLException {
    	return UtenteDAO.checkEmail(email);
    }
    
    /**
     * Aggiorna la password dell'utente
     * @param password: nuova password
     * @throws SQLException
     */
    public void aggiornaPassword(String password) throws SQLException {
        UtenteDAO.doUpdatePassword(password, this);
    }
    
    /**
     * Aggiorna l'email dell'utente
     * @param email: nuova email
     * @throws SQLException
     */
    public void aggiornaEmail(String email) throws SQLException {
    	UtenteDAO.doUpdateEmail(email, this);
    }
    
    /**
     * Elimina l'account utente
     * @param email: email dell'account da eliminare
     * @throws SQLException
     */
    public void eliminaUtente(String email) throws SQLException {
    	UtenteDAO.doDeleteUser(email);
    }
    
    /**
     * Recupera un utente a partire dalla sua email
     * @param user: email utente
     * @return Utente: account utente trovato
     * @throws SQLException
     */
    public Utente getPass(String user) throws SQLException{
    	return UtenteDAO.doRetrieveByKey(user);
    	
    }

    /* (non-Javadoc) @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return "Utente [email=" + email + ", password=" + password + ", nome=" + nome + ", cognome=" + cognome
				+ ", stato=" + stato + "]";
	}

	public static final String LOGGATO = "loggato"; 
	public static final String UNLOGGED = "unlogged";

	private String email = null, password= null, nome= null, cognome= null, stato = "unlogged", via = null, civico = null, cap = null, citta=null;

    private ArrayList<CartaDiCredito> carte = new ArrayList<>();
    private Carrello carrello = new Carrello();
    private List<Ordine> ordini = null;
}
