package bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import model.CarrelloDAO;
import model.OggettoCarrello;

public class Carrello {

	public Carrello() {}
	
	public Carrello(int idCarrello, int qtaProdotti, float prezzototale, String utente)
	{
		this.idCarrello = idCarrello;
		this.Qtaprodotti = qtaProdotti;
		this.prezzoTotale = prezzototale;
		this.utente = utente;
	}
	
	public int getIdCarrello() {
		return idCarrello;
	}

	public ArrayList<OggettoCarrello> getArticoli() {
		return articoli;
	}
	
	public void addArticolo(OggettoCarrello articolo) {
		this.articoli.add(articolo);
	}

	public void setArticoli(ArrayList<OggettoCarrello> articoli) {
		this.articoli = articoli;
	}

	public void setIdCarrello(int idCarrello) {
		this.idCarrello = idCarrello;
	}

	public int getQtaprodotti() {
		return Qtaprodotti;
	}

	public void setQtaprodotti(int qtaprodotti) {
		Qtaprodotti = qtaprodotti;
	}

	public float getPrezzoTotale() {
		return prezzoTotale;
	}

	public void setPrezzoTotale(float prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}
	
	public static Carrello recuperaCarrelloByUser(String email) throws SQLException {
		return CarrelloDAO.doRetrieveByUser(email);
	}
	
	public static void creaCarrello(int qtaProdotti, float prezzoTotale, String email) throws SQLException {
		CarrelloDAO.doCreate(qtaProdotti, prezzoTotale, email);
	}
	
	public void removeItem(int idProdotto) {
		Iterator<OggettoCarrello> iter = articoli.iterator();

		while (iter.hasNext()) {
		    OggettoCarrello articolo = iter.next();

		    if (articolo.getOggetto().getIdProdotto() == idProdotto)
		        iter.remove();
		}
	}
	
	public void addItem(OggettoCarrello articolo) {
		articoli.add(articolo);
	}
	
	public void aggiornaPrezzoCarrello(){
		this.prezzoTotale = 0;
		for(OggettoCarrello articolo: articoli) {
			this.prezzoTotale += (articolo.getOggetto().getPrezzo() * articolo.getQuantita());
		}
	}
	
	public int diminuisciQuantita(int idProdotto) {
		int newQuantita = 0;
		for(OggettoCarrello articolo: articoli) {
			int quantita = articolo.getQuantita();
			if(articolo.getOggetto().getIdProdotto() == idProdotto) {
				newQuantita=quantita-1;
				articolo.setQuantita(newQuantita);
			}
		}
		return newQuantita;
		
	}
	
	public int aumentaQuantita(int idProdotto) {
		int newQuantita = 0;
		for(OggettoCarrello articolo: articoli) {
			int quantita = articolo.getQuantita();
			if(articolo.getOggetto().getIdProdotto() == idProdotto) {
				newQuantita=quantita+1;
				articolo.setQuantita(newQuantita);
			}
		}
		return newQuantita;
		
	}

	private  int idCarrello, Qtaprodotti;
	private  float prezzoTotale;
	private  String utente;
	private ArrayList<OggettoCarrello> articoli = new ArrayList<OggettoCarrello>();
	
}
