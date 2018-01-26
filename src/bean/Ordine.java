package bean;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.OrdineDAO;

/**
 * Ordine utente
 * @author Antonio
 */
public class Ordine {
	
	public Ordine() {}
	
	public Ordine(int idOrdine, float costo, LocalDate dataAcquisto, String carta, String utente,
			List<Prodotto> articoli) {
		this.idOrdine = idOrdine;
		this.costo = costo;
		this.dataAcquisto = dataAcquisto;
		this.carta = carta;
		this.articoli = articoli;
		this.utente = utente;
	}

	public Ordine(int idOrdine, float costo, LocalDate dataAcquisto, String carta, String utente) {
		this.idOrdine = idOrdine;
		this.costo = costo;
		this.dataAcquisto = dataAcquisto;
		this.carta = carta;
		this.utente = utente;
	}

	public Ordine(float costo, LocalDate dataAcquisto, String carta, String utente, List<Prodotto> articoli) {
		this.costo = costo;
		this.dataAcquisto = dataAcquisto;
		this.carta = carta;
		this.articoli = articoli;
		this.utente = utente;
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public List<Prodotto> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Prodotto> articoli) {
		this.articoli = articoli;
	}

	public LocalDate getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDate dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}
	
	/**
	 * Inoltra l'ordine dell'utente (quando l'utente completa l'acquisto)
	 * @param ordine: Ordine dell'utente
	 * @return int: codice di successo
	 * @throws SQLException
	 */
	public static int writeOrder(Ordine ordine) throws SQLException {
		return OrdineDAO.inoltraOrdine(ordine);
	}
	
	/**
	 * Trova tutti gli ordini effettuati da un utente
	 * @param utente: utente di cui si voglione recuperare gli ordini
	 * @return List<Ordine>: lista di ordini
	 * @throws SQLException
	 */
	public static List<Ordine> searchOrdine(Utente utente) throws SQLException {
		return OrdineDAO.doRetrieveByUtente(utente);
	}

	private int idOrdine;
	private float costo;
	private String carta, utente;
	private LocalDate dataAcquisto;
	private List<Prodotto> articoli = new ArrayList<>();
}
