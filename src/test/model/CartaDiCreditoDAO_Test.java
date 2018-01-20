package test.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.CartaDiCredito;
import bean.Utente;
import model.CartaDiCreditoDAO;
import model.UtenteDAO;

/**
 * Classe di test per CartaDiCreditoDAO
 * @author Luca
 */
public class CartaDiCreditoDAO_Test {
	
	private CartaDiCredito carta;
	private Utente utente;
	
	@Before
	public void setUp() throws SQLException {
		
		//recupero un utente dal db
		utente = UtenteDAO.doRetrieveByKey("ndonio96@gmail.com");
		
		//creo la carta di credito di test
		carta = new CartaDiCredito(
			"5889 6822 1355 1234", 
			"Antonio Vivone", 
			LocalDate.parse("2020-04-10"), 
			670
		);
	}
	
	@Test
	public void test() throws SQLException { 
		
		//salvataggio carta di credito e associazione con utente
		CartaDiCreditoDAO.doSave(carta);
		CartaDiCreditoDAO.doSave(carta.getNumerocarta(), utente.getEmail());
		
		//recupero della carta salvata
		CartaDiCredito saved = CartaDiCreditoDAO.doRetrieveByKey(carta.getNumerocarta());
		
		assertEquals(carta.getNumerocarta(), saved.getNumerocarta());
	}
	
	@After
	public void clear() throws SQLException {
		
		//ripristino lo stato del db eliminando la carta aggiunta
		CartaDiCreditoDAO.doDelete(carta.getNumerocarta(), utente.getEmail());
	}
}
