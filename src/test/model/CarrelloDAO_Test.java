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
	
	@Before
	public void prepare() throws Exception {
		
		// utente di test
		utente = new Utente("test@email.it", "Nome", "Cognome", "testprova", "via", "12", "84090", "city");
		utente.setStato("loggato");
		UtenteDAO.doSave(utente);
		
		//aggiungo dei prodotti al carrello
		/*
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(1), 4);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(2), 3);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(3), 2);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(4), 1);
		*/
	}
	
	@Test //il test ha rivelato bug nelle varie dao e la query di update sbagliata in CarrelloDAO
	public void test_doCreate() throws SQLException {
		
		//aggiorno il carrello
		Carrello cart = Carrello.getCarrelloUtente(utente);
		
		assertNotNull(cart);
		assertTrue(carrello.getArticoli().size() == 0); //carrello è vuoto?
	}
	
	@After
	public void clean() throws SQLException {
		
		//cancello utente (il carrello è cancellato di conseguenza - delete on cascade)
		UtenteDAO.doDeleteUser(utente.getEmail());
		utente = null;
	}
}
