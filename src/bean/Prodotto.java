package bean;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


import model.ProdottoDAO;
import search.Findable;

public class Prodotto implements Findable {

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
	

}
