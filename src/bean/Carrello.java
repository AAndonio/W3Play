package bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import model.CarrelloDAO;
import util.IO;

public class Carrello {

	public Carrello() {}
	
	public Carrello(int idCarrello, int qtaProdotti, float prezzototale, Utente utente)
	{
		this.idCarrello = idCarrello;
		this.Qtaprodotti = qtaProdotti;
		this.prezzoTotale = prezzototale;
		this.utente = utente;
	}
	
	public int getIdCarrello() {
		return idCarrello;
	}

	public Map<Prodotto, Integer> getArticoli() {
		return articoli;
	}
	
	/** Ritorna i prodotti presenti nel carrello come una lista di coppie (prodotto, quantit‡) */
	public List<util.Entry<Prodotto, Integer>> getListaArticoli() {
		
		List<util.Entry<Prodotto, Integer>> list = new ArrayList<>();
		
		articoli.forEach((p, q) -> list.add(new util.Entry<Prodotto, Integer>(p, q)));
		
		return list;
	}
	
	public boolean carrelloVuoto() {
		
		return articoli.size() == 0;
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
	

	public Utente getUtente() {
		return utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	/**
	 * Recupera il carrello dal database, se l'utente non ha un carrello ne crea uno nuovo.
	 * @param utente
	 * @return Carrello
	 * @throws SQLException
	 */
	public static Carrello getCarrelloUtente(Utente utente) throws SQLException {	
		
		return CarrelloDAO.doRetrieveByUser(utente);
	}
	
	/**
	 * Recupera gli articoli salvati nel carrello, aggiornando quantit‡ e prezzo complessivi (anche nel DB).
	 */
	public void fetchArticoli() {
		
		articoli = CarrelloDAO.doLoad(idCarrello);
		
		Qtaprodotti  = 0;
		prezzoTotale = 0;
		
		articoli.forEach((p, i) -> { 
			Qtaprodotti  += i; 
			prezzoTotale += p.getPrezzo() * i; 
		});
		
		if (utente.isLoggato())
			CarrelloDAO.doUpdate(this);
		
		IO.printf("Carrello: quantit‡= %d, prezzo= %f\n", Qtaprodotti, prezzoTotale);
	}
	
	/**
	 * Aggiunge un prodotto al carrello e aggiorna il DB (solo se l'utente Ë loggato).
	 * @param prodotto
	 * @param quantit‡
	 * @throws Exception 
	 */
	public void addProdotto(Prodotto prodotto, int quantit‡) throws Exception {
		
		articoli.put(prodotto, quantit‡);
		
		Qtaprodotti  += quantit‡; 
		prezzoTotale += prodotto.getPrezzo() * quantit‡; 
		
		if (utente.isLoggato())
			CarrelloDAO.doAddItem(this, prodotto, quantit‡);
		
		IO.printf("Carrello: quantit‡= %d, prezzo= %f\n", Qtaprodotti, prezzoTotale);
	}
	
	/**
	 * Rimuove un prodotto dal carrello e aggiorna il DB (solo se l'utente Ë loggato)
	 * @param prodotto: prodotto da eliminare
	 * @throws SQLException 
	 */
	public void removeProdotto(Prodotto prodotto) throws SQLException {
		
		Integer amount = articoli.remove(prodotto);
		if (amount != null) {
			
			IO.printf("amount: %d, prezzo: %f, sub-prezzo: %f\n", amount, prodotto.getPrezzo(), prodotto.getPrezzo() * amount);
			
			Qtaprodotti  -= amount; 
			prezzoTotale -= prodotto.getPrezzo() * amount;
			
			IO.printf("Carrello: quantit‡= %d, prezzo= %f\n", Qtaprodotti, prezzoTotale);
			
			if (utente.isLoggato())
				CarrelloDAO.doDeleteItem(this, prodotto);
		}
	}
	
	
	/** controlla se nel carrello Ë presente il prodotto specificato */
	public boolean contieneProdotto(Prodotto prodotto) {
		
		return articoli.containsKey(prodotto);
	}	
	
	/**
	 * Metodo di supporto per la modifica della quantit‡ del prodotto nel carrello
	 * @throws SQLException
	 */
	public void aggiornaQuantit‡Prodotto(int idProdotto, int diQuanto) throws SQLException {
		
		Set<Entry<Prodotto, Integer>> set = articoli.entrySet();
		
		for (Entry<Prodotto, Integer> entry: set) {
			
			Prodotto p = entry.getKey();
			Integer  q = entry.getValue(); 
			
			if (p.getIdProdotto() == idProdotto) {
				
				q += diQuanto;
				
				if (q <= 0) { 
					
					removeProdotto(p);
					break;
				}
				
				articoli.put(p, q);
				
				Qtaprodotti  += diQuanto; 
				prezzoTotale += p.getPrezzo() * diQuanto; 
				
				if (utente.isLoggato())
					CarrelloDAO.doUpdateItemQuantity(this, p, q);
				
				IO.printf("Carrello: quantit‡= %d, prezzo= %f\n", Qtaprodotti, prezzoTotale);
				break;
			}
		}
	}
	
	/** Fonde i due carrelli, riflettendo le modifiche sul DB */
	public void merge(Carrello carrello) {
		
		List<util.Entry<Prodotto, Integer>> nuoviProdotti = carrello.getListaArticoli();
		
		nuoviProdotti.forEach((e) -> {
			
			IO.println("Carrello.merge");
			
			Prodotto prodotto = e.getKey();
			int quantit‡ = e.getValue();
			
			if (Carrello.this.contieneProdotto(prodotto)) {
				
				try {
					Carrello.this.aggiornaQuantit‡Prodotto(prodotto.getIdProdotto(), quantit‡);
					
				} catch (SQLException e1) {
					IO.println("Carrello.merge: errore nell'aggiornare la quantit‡ del prodotto");
					e1.printStackTrace();
				}
				
			} else {
				
				try {
					Carrello.this.addProdotto(prodotto, quantit‡);
					
				} catch (Exception e1) {
					IO.println("Carrello.merge: errore inserire un prodotto");
					e1.printStackTrace();
				}
			}
		});
		
		IO.printf("Carrello: quantit‡= %d, prezzo= %f\n", Qtaprodotti, prezzoTotale);
	}
	
	/**
	 * Rimuove tutti gli articoli dal Carrello (e dal DB)
	 */
	public void svuota() {
		
		try {
			CarrelloDAO.doFlush(this);
			
		} catch (SQLException e) {
			IO.println("Carrello.svuotaCarrello: errore!");
			e.printStackTrace();
			
		} finally {
			
			Qtaprodotti  = 0;
			prezzoTotale = 0;
			articoli = new TreeMap<>();
			
			if (utente.isLoggato())
				CarrelloDAO.doUpdate(this);
			
			IO.printf("Carrello: quantit‡= %d, prezzo= %f\n", Qtaprodotti, prezzoTotale);
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder("Carrello:\n");
		
		this.getListaArticoli().forEach((e) -> 
			sb.append("\tProdotto: " + e.getKey().getNome())
			  .append(" Quantit‡: " + e.getValue())
			  .append("\n")
		);
		
		sb.append("Quantit‡ totale: " + Qtaprodotti)
		  .append("\nPrezzo totale: " + prezzoTotale);
		
		return sb.toString();
	}
	
	private int idCarrello, Qtaprodotti = 0;
	private float prezzoTotale = 0f;
	private Utente utente;
	private Map<Prodotto, Integer> articoli = new TreeMap<>();
}
