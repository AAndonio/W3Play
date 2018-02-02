package bean;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.CartaDiCreditoDAO;

/**
 * Classe per la carta di credito utente
 * @author Alfonso
 */
public class CartaDiCredito {

	public CartaDiCredito() {}
	
	public CartaDiCredito(String numerocarta, String titolare, LocalDate scadenza, int ccv)
	{
		this.numerocarta = numerocarta;
		this.titolare = titolare;
		this.scadenza = scadenza;
		this.CCV = ccv;
	}

	public String getNumerocarta() {
		return numerocarta;
	}

	public void setNumerocarta(String numerocarta) {
		this.numerocarta = numerocarta;
	}

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}

	public LocalDate getScadenza() {
		return scadenza;
	}

	public void setScadenza(LocalDate scadenza) {
		this.scadenza = scadenza;
	}

	public int getCCV() {
		return CCV;
	}

	public void setCCV(int cCV) {
		CCV = cCV;
	}
	
	/**
	 * Recupera, dal database, una lista di carte di credito corrispondente all'utente specificato
	 * @param email: email dell'utente
	 * @return ArrayList<CartaDiCredito>: lista di carte di credito
	 * @throws SQLException
	 */
	public static ArrayList<CartaDiCredito> recuperaCarteByUtente(String email) throws SQLException{
		return CartaDiCreditoDAO.doRetrieveByUser(email);
	}
	
	/**
	 * Aggiunge, nel database, una carta di credito
	 * @param carta: CartaDiCredito da salvare
	 * @throws SQLException
	 */
	public static void addCreditCard(CartaDiCredito carta) throws SQLException {
		CartaDiCreditoDAO.doSave(carta);
	}
	
	/**
	 * Inserisce, nel database, la relazione tra utente e carta di credito
	 * @param carta: carta di credito di utente
	 * @param user: utente con cui associare la carta di credito
	 * @throws SQLException
	 */
	public static void associaCartaUtente(String carta, String user) throws SQLException {
		CartaDiCreditoDAO.doSave(carta, user);
	}
	
	/**
	 * Rimuove, dal database, l'associazione della carta di credito con l'utente
	 * @param carta: carta di credito di utente
	 * @param user: utente di cui si vuole rimuovere la carta di credito
	 * @throws SQLException
	 */
	public static void eliminaAssociazione(String carta, String user) throws SQLException {
		CartaDiCreditoDAO.doDelete(carta,user);
	}
	
	/**
	 * Aggiorna, nel database, i dati della carta di credito
	 * @param vecchia: id della vecchia carta di credito
	 * @param nuova: nuova carta di credito
	 * @throws SQLException
	 */
	public static void updateCarta(String vecchia, CartaDiCredito nuova) throws SQLException {
		CartaDiCreditoDAO.doUpdate(vecchia,nuova);
	}

	private  String numerocarta=null, titolare=null;
	private  LocalDate scadenza=null;
	private  int CCV;
}
