package test.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.Carrello;
import bean.Prodotto;
import bean.Utente;
import model.CarrelloDAO;
import model.ProdottoDAO;
import model.UtenteDAO;
import utils.DBConnection;

/**
 * Classe di test per CarrelloDAO
 * @author Luca
 */
public class CarrelloDAO_Test {
	
	private Utente utente;
	
	@Before
	public void prepare() throws Exception {
		
		// utente di test
		utente = new Utente("test@email.it", "Nome", "Cognome", "testprova", "via", "12", "84090", "city");
		utente.setStato(Utente.LOGGATO);
		UtenteDAO.doSave(utente);
	}
	
	@Test
	public void test_doCreate() throws SQLException {
		
		//aggiungo un nuovo carrello nel db
		CarrelloDAO.doCreate(utente.getEmail());
		
		//
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement("SELECT * FROM carrello WHERE utente = ?");
		pstmt.setString(1, utente.getEmail());
		
		ResultSet rs = pstmt.executeQuery();
		
		boolean result = rs.next();
		
		rs.close();
		pstmt.close();
		con.close();
		
		assertTrue(result);	
	}
	
	@Test 
	public void test_doUpdate() throws Exception {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		int quantita = carrello.getQtaprodotti();
		float prezzo = carrello.getPrezzoTotale();
		
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(1), 4);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(2), 3);
		
		Carrello carrello2 = CarrelloDAO.doRetrieveByKey(carrello.getIdCarrello());
		
		assertNotNull(carrello2);
		assertTrue(quantita != carrello2.getQtaprodotti() && prezzo != carrello2.getPrezzoTotale());
	}
	
	@Test
	public void test_doRetriveByKey() throws SQLException {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		Carrello carrello2 = CarrelloDAO.doRetrieveByKey(carrello.getIdCarrello());
		
		assertNotNull(carrello2);
	}
	
	@Test
	public void test_doRetriveByUser() throws SQLException {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		assertNotNull(carrello);
	}
	
	@Test
	public void test_doLoad() throws Exception {
		
		//
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(1), 4);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(2), 3);
		
		//
		Carrello carrello2 = Carrello.getCarrelloUtente(utente);
		carrello2.fetchArticoli();
		
		assertTrue(carrello2.getArticoli().size() > 0);
	}
	
	@Test
	public void test_addItem() throws Exception {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		Prodotto prodotto = ProdottoDAO.doRetrieveByKey(1);
		CarrelloDAO.doAddItem(carrello, prodotto, 4);
		
		//
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement("SELECT count(*) FROM inserire WHERE carrello = ? and prodotto = ?;");
		pstmt.setInt(1, carrello.getIdCarrello());
		pstmt.setInt(2, prodotto.getIdProdotto());
		
		ResultSet rs = pstmt.executeQuery();
		
		int result = (rs.next()) ? rs.getInt(1) : 0; 
			
		rs.close();
		pstmt.close();
		con.close();
		
		assertTrue(result > 0);
	}
	
	@Test
	public void test_doDeleteItem() throws Exception {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		Prodotto prodotto = ProdottoDAO.doRetrieveByKey(1);
		
		CarrelloDAO.doAddItem(carrello, prodotto, 4);
		CarrelloDAO.doDeleteItem(carrello, prodotto);
		
		//
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement("SELECT count(*) FROM inserire WHERE carrello = ? and prodotto = ?;");
		pstmt.setInt(1, carrello.getIdCarrello());
		pstmt.setInt(2, prodotto.getIdProdotto());
		
		ResultSet rs = pstmt.executeQuery();
		
		int result = (rs.next()) ? rs.getInt(1) : 0; 
			
		rs.close();
		pstmt.close();
		con.close();
		
		assertTrue(result == 0);
	}
	
	@Test
	public void test_doUpdateItemQuantity() throws Exception {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		Prodotto prodotto = ProdottoDAO.doRetrieveByKey(1);
		int quantita = 100;
		
		CarrelloDAO.doAddItem(carrello, prodotto, 4);
		CarrelloDAO.doUpdateItemQuantity(carrello, prodotto, quantita);
		
		//
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement("SELECT quantita FROM inserire WHERE carrello = ? and prodotto = ?;");
		pstmt.setInt(1, carrello.getIdCarrello());
		pstmt.setInt(2, prodotto.getIdProdotto());
		
		ResultSet rs = pstmt.executeQuery();
		
		int result = (rs.next()) ? rs.getInt(1) : 0; 
			
		rs.close();
		pstmt.close();
		con.close();
		
		assertTrue(result == quantita);
	}
	
	@Test
	public void test_doFlush() throws Exception {
		
		Carrello carrello = Carrello.getCarrelloUtente(utente);
		utente.setCarrello(carrello);
		
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(1), 4);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(2), 3);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(3), 2);
		carrello.addProdotto(ProdottoDAO.doRetrieveByKey(4), 1);
		
		CarrelloDAO.doFlush(carrello);
		
		//
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement("SELECT count(*) FROM inserire WHERE carrello = ?");
		pstmt.setInt(1, carrello.getIdCarrello());
		
		ResultSet rs = pstmt.executeQuery();
		
		int result = (rs.next()) ? rs.getInt(1) : 0; 
			
		rs.close();
		pstmt.close();
		con.close();
		
		assertTrue(result == 0);
	}
	
	@After
	public void clean() throws SQLException {
		
		//cancello utente (il carrello è cancellato di conseguenza - delete on cascade)
		UtenteDAO.doDeleteUser(utente.getEmail());
		utente = null;
	}
}
