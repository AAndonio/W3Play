package test.model;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.CartaDiCredito;
import bean.Utente;
import model.CartaDiCreditoDAO;
import model.UtenteDAO;
import utils.DBConnection;

/**
 * Classe di test per {@link CartaDiCreditoDAO}
 * @author Alfonso
 */
public class CartaDiCreditoDAO_Test {

	private CartaDiCredito carta;
	private Utente utente;

	@Before
	public void setUp() throws SQLException {

		// utente di test
		utente = new Utente("test@email.it", "Nome", "Cognome", "testprova", "via", "12", "84090", "city");
		UtenteDAO.doSave(utente);

		// creo la carta di credito di test
		carta = new CartaDiCredito("5889 6822 1355 1234", "Nome Cognome", LocalDate.parse("2020-04-10"), 670);
	}

	@Test // aggiunto controllo su associazioni con carta di credito, se le #associazioni == 0, la carta viene rimossa.
	public void testDoSave() throws SQLException {
		
		// salvataggio carta di credito e associazione con utente
		CartaDiCreditoDAO.doSave(carta);
		CartaDiCreditoDAO.doSave(carta.getNumerocarta(), utente.getEmail());

		// recupero della carta salvata
		CartaDiCredito saved = CartaDiCreditoDAO.doRetrieveByKey(carta.getNumerocarta());

		assertEquals(carta.getNumerocarta(), saved.getNumerocarta());
	}

	@Test
	public void testDoRetriveByKey() throws SQLException {
		
		CartaDiCreditoDAO.doSave(carta);
		
		//
		CartaDiCredito cartaTest = CartaDiCreditoDAO.doRetrieveByKey(carta.getNumerocarta());

		assertEquals(carta.getNumerocarta(), cartaTest.getNumerocarta());

	}

	@Test
	public void testDoRetrieveByUser() throws SQLException {

		CartaDiCredito.addCreditCard(carta);
		utente.addCarta(carta);
		
		CartaDiCreditoDAO.doSave(carta.getNumerocarta(), utente.getEmail());

		List<CartaDiCredito> array = CartaDiCreditoDAO.doRetrieveByUser(utente.getEmail());
		
		assertEquals(carta.getNumerocarta(), array.get(0).getNumerocarta());
	}

	@Test
	public void testDoSaveAssociazione() throws SQLException {
		
		CartaDiCredito.addCreditCard(carta);
		utente.addCarta(carta);
		
		int count1 = countAssociazioniCarta(carta);

		CartaDiCreditoDAO.doSave(carta.getNumerocarta(), utente.getEmail());
		
		int count2 = countAssociazioniCarta(carta);
		
		// #associazioni dopo il save (count2) > #associazioni prima del save (count1)
		assertTrue(count2 > count1);
	}

	@Test
	public void testDoDeleteAssociazione() throws SQLException {

		CartaDiCredito.addCreditCard(carta);
		utente.addCarta(carta);

		CartaDiCreditoDAO.doSave(carta.getNumerocarta(), utente.getEmail());
		int count1 = countAssociazioniCarta(carta);
		
		CartaDiCreditoDAO.doDelete(carta.getNumerocarta(), utente.getEmail());
		int count2 = countAssociazioniCarta(carta);

		// #associazioni dopo il save (count1) > #associazioni dopo il delete (count2)
		assertTrue(count2 < count1);
	}
	
	@Test
	public void testDoDelete() throws SQLException {
		
		CartaDiCredito.addCreditCard(carta);
		utente.addCarta(carta);

		CartaDiCreditoDAO.doSave(carta.getNumerocarta(), utente.getEmail());
		CartaDiCreditoDAO.doDelete(carta.getNumerocarta(), utente.getEmail());
		
		CartaDiCredito carta2 = CartaDiCreditoDAO.doRetrieveByKey(carta.getNumerocarta());
		
		assertNull(carta2);
	}
	
	private int countAssociazioniCarta(CartaDiCredito carta) {
		
		try {
		Connection connection = DBConnection.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("SELECT count(*) FROM associare WHERE Carta = ?");
		statement.setString(1,  carta.getNumerocarta());
        
        ResultSet rs = statement.executeQuery();
        if (rs.next())
        	
        	return rs.getInt(1);
        else
        	return 0;
        
		} catch (Exception e) {
			
			return 0;
		}
	}

	@After
	public void clear() throws SQLException {

		// ripristino lo stato del db eliminando la carta aggiunta e l'utente
		CartaDiCreditoDAO.doDelete(carta.getNumerocarta(), utente.getEmail());
		UtenteDAO.doDeleteUser(utente.getEmail());
	}
}
