package bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.ProdottoDAO;
import search.Findable;

/**
 * Prodotto, rappresenta un articolo presente nel catalogo e che può essere acquistato da un utente
 * @author Augusto
 */
public class Prodotto implements Serializable, Findable, Comparable<Prodotto> {
	
	private static final long serialVersionUID = -1431930434219319963L;

	public Prodotto() {}
	
	public Prodotto(int idProdotto, String nome, String produttore, String piattaforma, String genere, 
					String descrizione, String immagini, float prezzo, int disponibilita, LocalDate dataUscita, int venduti, String linkVideo)
	{
		this.idProdotto = idProdotto;
		this.nome = nome;
		this.produttore = produttore;
		this.piattaforma = piattaforma;
		this.genere = genere;
		this.descrizione = descrizione;
		this.immagine = immagini;
		this.prezzo = prezzo;
		this.disponibilita = disponibilita;
		this.dataUscita = dataUscita;
		this.venduti=venduti;
		this.linkVideo=linkVideo;
	}
	
	public Prodotto(int idProdotto, String nome, String produttore, String piattaforma, String genere, 
			String descrizione, String immagini, float prezzo, int disponibilita, LocalDate dataUscita, String linkVideo)
	{
		this.idProdotto = idProdotto;
		this.nome = nome;
		this.produttore = produttore;
		this.piattaforma = piattaforma;
		this.genere = genere;
		this.descrizione = descrizione;
		this.immagine = immagini;
		this.prezzo = prezzo;
		this.disponibilita = disponibilita;
		this.dataUscita = dataUscita;
		this.linkVideo=linkVideo;
	}
	
	public int getVenduti() {
		return venduti;
	}

	public void setVenduti(int venduti) {
		this.venduti = venduti;
	}

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public LocalDate getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(LocalDate dataUscita) {
		this.dataUscita = dataUscita;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public String getNome() {
		return nome;
	}

	public String getProduttore() {
		return produttore;
	}

	public String getPiattaforma() {
		return piattaforma;
	}

	public String getGenere() {
		return genere;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getImmagine() {
		return immagine;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setProduttore(String produttore) {
		this.produttore = produttore;
	}
	
	public void setPiattaforma(String piattaforma) {
		this.piattaforma = piattaforma;
	}
	
	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	
	/**
	 * Ricerca una serie di prodotti all'interno del database
	 * @param searchQuery: query di ricerca inserita dell'utente
	 * @return ArrayList<Prodotto>: lista dei prodotti trovati
	 * @throws SQLException
	 */
	public static ArrayList<Prodotto> searchProduct(String searchQuery) throws SQLException{
		return ProdottoDAO.search(searchQuery);
	}
	
	/**
	 * Recupera gli ultimi prodotti inseriti nel database
	 * @return ArrayList<Prodotto>: lista di prodotti
	 * @throws SQLException
	 */
	public static ArrayList<Prodotto> ultimiArrivi() throws SQLException {
		return ProdottoDAO.ultimiArrivi();
	}
	
	/**
	 * Recupera i prodotti più venduti
	 * @return ArrayList<Prodotto>: lista di prodotti
	 * @throws SQLException
	 */
	public static ArrayList<Prodotto> piuVenduti() throws SQLException {
		return ProdottoDAO.piuVenduti();
	}
	
	/**
	 * Cerca un prodotto dal menu
	 * @param ProdOrPiatt: prodotto o piattaforma
	 * @param nome: nome del menu
	 * @return ArrayList<Prodotto>: lista di prodotti
	 * @throws SQLException
	 */
	public static ArrayList<Prodotto> searchFromMenuNav(String ProdOrPiatt, String nome) throws SQLException {
		return ProdottoDAO.searchFromMenuNav(ProdOrPiatt,nome);
	}
	
	/**
	 * Cerca i prodotti presenti nel menu delle console
	 * @param nome: nome del prodotto cercato
	 * @return ArrayList<Prodotto>: lista di prodotti
	 * @throws SQLException
	 */
	public static ArrayList<Prodotto> searchFromMenuConsole(String nome) throws SQLException {
		return ProdottoDAO.searchFromMenuConsole(nome);
	}
	
	/**
	 * Cerca i prodotti presenti nel menu giochi
	 * @param ProdOrPiatt: prodotto o piattaforma
	 * @return ArrayList<Prodotto>: lista di prodotti
	 * @throws SQLException
	 */
	public static ArrayList<Prodotto> searchFromMenuGiochi(String ProdOrPiatt) throws SQLException {
		return ProdottoDAO.searchFromMenuGiochi(ProdOrPiatt);
	}
	
	/**
	 * Aggiunge un nuovo prodotto al catalogo
	 * @param prodotto: prodotto da aggiungere
	 * @throws SQLException
	 */
	public static void addProduct(Prodotto prodotto) throws SQLException{
		ProdottoDAO.doSave(prodotto);
	}
	
	/**
	 * Aggiorna un prodotto
	 * @param prodotto: prodotto da aggiornare
	 * @throws SQLException
	 */
	public static void updateProduct(Prodotto prodotto) throws SQLException{
		ProdottoDAO.doUpdate(prodotto);
	}
	
	/**
	 * Acquista un prodotto
	 * @throws SQLException
	 */
	public void vendi() throws SQLException {
		disponibilita--;
		venduti++;
		ProdottoDAO.vendi(this);
	}

	private int idProdotto, disponibilita, venduti;
	private String nome, produttore, piattaforma, genere, descrizione, immagine, linkVideo;
	private float prezzo;
	private LocalDate dataUscita;
	
	/* (non-Javadoc) */
	@Override
	public String getIndex() {
		
		return nome; 
	}

	/* (non-Javadoc) @see java.lang.Object#hashCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genere == null) ? 0 : genere.hashCode());
		result = prime * result + idProdotto;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((produttore == null) ? 0 : produttore.hashCode());
		return result;
	}

	/* (non-Javadoc) @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Prodotto other = (Prodotto) obj;
		if (idProdotto != other.idProdotto)
			return false;
		
		return true;
	}
	
	/* (non-Javadoc) @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Prodotto o) {
		
		Integer id1 = idProdotto;
		Integer id2 = o.idProdotto;
		
		return id1.compareTo(id2);
	}
}
