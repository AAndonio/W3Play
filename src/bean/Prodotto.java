package bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


import model.ProdottoDAO;
import search.Findable;

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
	
	
	public static ArrayList<Prodotto> searchProduct(String prodotto) throws SQLException{
		return ProdottoDAO.search(prodotto);
	}
	
	public static ArrayList<Prodotto> ultimiArrivi() throws SQLException {
		return ProdottoDAO.ultimiArrivi();
	}

	public static ArrayList<Prodotto> piuVenduti() throws SQLException {
		return ProdottoDAO.piuVenduti();
	}
	
	public static ArrayList<Prodotto> searchFromMenuNav(String ProdOrPiatt, String nome) throws SQLException {
		return ProdottoDAO.searchFromMenuNav(ProdOrPiatt,nome);
	}
	
	public static ArrayList<Prodotto> searchFromMenuConsole(String nome) throws SQLException {
		return ProdottoDAO.searchFromMenuConsole(nome);
	}
	public static ArrayList<Prodotto> searchFromMenuGiochi(String ProdOrPiatt) throws SQLException {
		return ProdottoDAO.searchFromMenuGiochi(ProdOrPiatt);
	}
	
	public static void addProduct(Prodotto prodotto) throws SQLException{
		ProdottoDAO.doSave(prodotto);
	}
	
	public static void updateProduct(Prodotto prodotto) throws SQLException{
		ProdottoDAO.doUpdate(prodotto);
	}
	
	public void vendi() throws SQLException {
		disponibilita--;
		venduti++;
		ProdottoDAO.vendi(this);
	}


	private int idProdotto, disponibilita, venduti;
	private String nome, produttore, piattaforma, genere, descrizione, immagine, linkVideo;
	private float prezzo;
	private LocalDate dataUscita;
	
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

	@Override
	public int compareTo(Prodotto o) {
		
		Integer id1 = idProdotto;
		Integer id2 = o.idProdotto;
		
		return id1.compareTo(id2);
	}
}
