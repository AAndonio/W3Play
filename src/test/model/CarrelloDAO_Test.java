package test.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.Carrello;
import bean.Prodotto;
import bean.Utente;
import model.CarrelloDAO;
import model.OggettoCarrello;
import model.ProdottoDAO;
import model.UtenteDAO;
import util.IO;

/**
 * Classe di test per CarrelloDAO
 * @author Luca
 */
public class CarrelloDAO_Test {
	
	private Utente utente;
	private Carrello carrello;
	private List<Prodotto> prodotti = new ArrayList<>();
	private List<OggettoCarrello> items = new ArrayList<>();
	
	@Before
	public void prepare() throws SQLException {
		
		//recupero l'utente
		utente = UtenteDAO.doRetrieveByKey("ndonio96@gmail.com");
		
		//recupero il carrello dell'utente
		carrello = Carrello.recuperaCarrelloByUser(utente.getEmail());
		int id = carrello.getIdCarrello();
		
		//aggiungo dei prodotti al carrello
		prodotti.add(ProdottoDAO.doRetrieveByKey(1));
		prodotti.add(ProdottoDAO.doRetrieveByKey(2));
		prodotti.add(ProdottoDAO.doRetrieveByKey(3));
		prodotti.add(ProdottoDAO.doRetrieveByKey(4));
		
		prodotti.forEach((p) -> { 
			
			OggettoCarrello item = new OggettoCarrello(p, (int) (Math.random() * 10));
			
			try {
	
				OggettoCarrello.addItemToCart(item, id);
				
			} catch (SQLException e) { }
			
			items.add(item);
		});
	}
	
	@Test //il test ha rivelato bug nelle varie dao e la query di update sbagliata in CarrelloDAO
	public void test() throws SQLException {
		
		IO.print(carrello.getUtente());
		
		//aggiorno il carrello
		CarrelloDAO.doSave(carrello);
		
		Carrello cart = CarrelloDAO.doRetrieveByKey(carrello.getIdCarrello());
		assertNotNull(cart);
		
		assertTrue(carrello.getQtaprodotti() == cart.getQtaprodotti() && carrello.getPrezzoTotale() == cart.getPrezzoTotale());
	}
	
	@After
	public void clean() throws SQLException {
		
		int id = carrello.getIdCarrello();
		
		//rimuovendo i prodotti
		items.forEach((i) -> {
			
			try {
				
				OggettoCarrello.removeItemFromCart(i.getOggetto().getIdProdotto(), id);
				
			} catch (SQLException e) { }
		});
		
		carrello = null;
		utente = null;
	}
}
