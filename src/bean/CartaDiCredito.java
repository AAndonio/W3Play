package bean;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.CartaDiCreditoDAO;

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
	
	public static ArrayList<CartaDiCredito> recuperaCarteByUtente(String email) throws SQLException{
		return CartaDiCreditoDAO.doRetrieveByUser(email);
	}
	
	public static void addCreditCard(CartaDiCredito carta) throws SQLException {
		CartaDiCreditoDAO.doSave(carta);
	}
	
	public static void associaCartaUtente(String carta, String user) throws SQLException {
		CartaDiCreditoDAO.doSave(carta, user);
	}
	
	public static void eliminaAssociazione(String carta, String user) throws SQLException {
		CartaDiCreditoDAO.doDelete(carta,user);
	}
	
	public static void updateCarta(String vecchia, CartaDiCredito nuova) throws SQLException {
		CartaDiCreditoDAO.doUpdate(vecchia,nuova);
	}

	private  String numerocarta=null, titolare=null;
	private  LocalDate scadenza=null;
	private  int CCV;
}
