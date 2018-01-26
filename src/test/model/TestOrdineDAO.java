package test.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.*;

import bean.CartaDiCredito;
import bean.Ordine;
import bean.Prodotto;
import bean.Utente;
import model.CartaDiCreditoDAO;
import model.OrdineDAO;
import model.ProdottoDAO;
import model.UtenteDAO;
import utils.DBConnection;

/**
 * Classe di test per {@link OrdineDAO}
 * @author Augusto
 */
public class TestOrdineDAO {
	
	private Ordine ordine;
	private Utente utente;
	private CartaDiCredito carta;
	
	@Before
	public void setUp() throws Exception {
		
		utente = new Utente("test@email.it", "Nome", "Cognome", "testprova", "via", "12", "84090", "city");
		UtenteDAO.doSave(utente);
		
		carta = new CartaDiCredito("0000 0000 0000 0000", "Nome Cognome", LocalDate.parse("2020-06-20"), 600);
		utente.addCarta(carta);
		
		ordine = new Ordine(0, 0, LocalDate.parse("2018-01-24"), carta.getNumerocarta(), utente.getEmail());
		List<Prodotto> articoli = ordine.getArticoli();
		articoli.add(ProdottoDAO.doRetrieveByKey(1));
		articoli.add(ProdottoDAO.doRetrieveByKey(2));
		articoli.add(ProdottoDAO.doRetrieveByKey(3));
	}

	@Test //testa inoltraOrdine e doRetriveByUtente
	public void inoltraOrdine_e_doRetriveByUtente() throws SQLException {
		
		int id = OrdineDAO.inoltraOrdine(ordine);
		ordine.setIdOrdine(id);
		
		Assert.assertNotNull(OrdineDAO.doRetrieveByUtente(utente));
	}

	@After
	public void clear() throws Exception {
		
		Connection con = DBConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("delete from dettagliordine where ordine = ?");
		stmt.setInt(1, ordine.getIdOrdine());
		stmt.executeUpdate();
		stmt = con.prepareStatement("delete from ordine where utente = ?");
		stmt.setString(1, ordine.getUtente());
		stmt.executeUpdate();
		stmt.close();
		con.close();
		ordine = null;
		
		CartaDiCreditoDAO.doDelete(carta.getNumerocarta(), utente.getEmail());
		carta = null;
		
		UtenteDAO.doDeleteUser(utente.getEmail());
		utente = null;
	}
}