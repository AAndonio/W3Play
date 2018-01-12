package bean;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.OggettoOrdine;
import model.OrdineDAO;

public class Ordine {

	public Ordine() {}
	
	public Ordine(int idOrdine, float costo, LocalDate dataAcquisto,String carta, String utente, ArrayList<OggettoOrdine> articoli)
	{
		this.idOrdine = idOrdine;
		this.costo = costo;
		this.dataAcquisto = dataAcquisto;
		this.carta = carta;
		this.articoli = articoli;
		this.utente = utente;
	}
	
	public Ordine(int idOrdine, float costo, LocalDate dataAcquisto,String carta, String utente)
	{
		this.idOrdine = idOrdine;
		this.costo = costo;
		this.dataAcquisto = dataAcquisto;
		this.carta = carta;
		this.utente = utente;
	}
	
	public Ordine( float costo, LocalDate dataAcquisto,String carta, String utente, ArrayList<OggettoOrdine> articoli)
	{
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

	public ArrayList<OggettoOrdine> getArticoli() {
		return articoli;
	}

	public void setArticoli(ArrayList<OggettoOrdine> articoli) {
		this.articoli = articoli;
	}

	public LocalDate getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDate dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}
	
	public static int writeOrder(Ordine ordine) throws SQLException {
		return OrdineDAO.inoltraOrdine(ordine);
	}
	
	public static ArrayList<Ordine> searchOrdine(String utente) throws SQLException {
		return OrdineDAO.doRetrieveByUtente(utente);
	}
	
	private  int idOrdine;
	private float costo;
	private  String carta, utente;
	private  LocalDate dataAcquisto;
	private ArrayList<OggettoOrdine> articoli;

}
