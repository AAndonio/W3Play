package model;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Prodotto;

public class OggettoCarrello {
	
	public OggettoCarrello() {}
	
	public OggettoCarrello(Prodotto oggetto, int quantita) {
		this.oggetto = oggetto;
		this.quantita = quantita;
	}
	
	
	public Prodotto getOggetto() {
		return oggetto;
	}
	public void setOggetto(Prodotto oggetto) {
		this.oggetto = oggetto;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	
	public static ArrayList<OggettoCarrello> recuperaArticoliByCarrello(int cart) throws SQLException{
		return OggettoCarrelloDAO.doLoad(cart);
	}
	
	public static void removeItemFromCart(int idProdotto, int idCarrello) throws SQLException {
		OggettoCarrelloDAO.doDeleteItemByIdCart(idProdotto, idCarrello);
	}
	
	public static void removeItemFromCart (int idCarrello) throws SQLException {
		OggettoCarrelloDAO.doDeleteItemByCart(idCarrello);
	}
	
	public static void addItemToCart(OggettoCarrello articolo, int idCarrello) throws SQLException {
		OggettoCarrelloDAO.doLoadItemByCart(articolo, idCarrello);
	}
	
	public static void updateItemQuantity(int carrello, int prodotto, int quantita) throws SQLException {
		OggettoCarrelloDAO.doUpdateItemQuantity(carrello, prodotto, quantita);
	}
	
	
	
	private Prodotto oggetto = null;
	private int quantita;

}
