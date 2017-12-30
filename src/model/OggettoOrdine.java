package model;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Ordine;
import bean.Prodotto;

public class OggettoOrdine {

	public OggettoOrdine() {}
	
	public OggettoOrdine(Prodotto product, int quantita) {
		this.prodotto = product;
		this.quantita = quantita;
	}
	
	
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public Prodotto getProdotto() {
		return prodotto;
	}
	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}
	
	public static void addItemByOrder(Ordine ordine) throws SQLException {
		OggettoOrdineDAO.addItem(ordine);
	}
	
	public static ArrayList<Ordine> recuperaOggetti(ArrayList<Ordine> ordini) throws SQLException {
		return OggettoOrdineDAO.recuperaItemAllOrdersOfAnUser(ordini);
	}



	private int quantita;
	private Prodotto prodotto=null;
}
