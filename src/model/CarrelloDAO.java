package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import bean.Carrello;
import bean.Prodotto;
import bean.Utente;
import util.IO;
import utils.DBConnection;

public class CarrelloDAO {
	
	/**
	 * Aggiorna quantità e prezzo del carrello
	 * @throws SQLException
	 */
	public static void doUpdate(Carrello c) {
		
		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = DBConnection.getConnection();
	
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, c.getQtaprodotti());
			pstmt.setFloat(2, c.getPrezzoTotale());
	
			// fix del fix: parametri sbagliati e query update sbagliata >.<
			pstmt.setInt(3, c.getIdCarrello());
	
			pstmt.executeUpdate();
	
			pstmt.close();
			con.close();
		
		} catch (SQLException e) {
			IO.println("CarrelloDAO.doUpdate: errore!");
			e.printStackTrace();
		}
	}

	public static void doCreate(String email) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(CREATE_CART);
		pstmt.setString(1, email);

		pstmt.executeUpdate();

		pstmt.close();
		con.close();

	}

	public static Carrello doRetrieveByKey(int idCarrello) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(FIND_BY_ID);
		pstmt.setInt(1, idCarrello);
		ResultSet rs = pstmt.executeQuery();

		// fix: operation not allowed after result set is closed
		Carrello carrello = converti(rs);

		rs.close();
		pstmt.close();
		con.close();

		return carrello;

	}

	public static Carrello doRetrieveByUser(Utente utente) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(LOAD_CART_BY_EMAIL);
		pstmt.setString(1, utente.getEmail());
		ResultSet rs = pstmt.executeQuery();

		// fix: operation not allowed after result set is closed
		Carrello carrello = converti(rs);

		// se il carello non esiste per l'utente, lo crea
		if (carrello == null) {

			carrello = new Carrello();
			doCreate(utente.getEmail());
		}

		rs.close();
		pstmt.close();
		con.close();

		return carrello;
	}

	//-----------------------------------------------------------------------------
	//-- Gestione Articoli
	//-----------------------------------------------------------------------------
	
	/** Carica dal DB i prodotti salvati nel carrello specificato */
	public static Map<Prodotto, Integer> doLoad(int carrello) {

		Map<Prodotto, Integer> map = new TreeMap<>();

		try {
			Connection con = null;
			PreparedStatement pstmt = null;
			con = DBConnection.getConnection();

			pstmt = con.prepareStatement(LOAD_CART_ITEMS);
			pstmt.setInt(1, carrello);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				Prodotto prodotto = new Prodotto(
						rs.getInt(1),    rs.getString(2), 
						rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), 
						rs.getString(7), rs.getFloat(8), 
						rs.getInt(9),    rs.getDate(10).toLocalDate(), 
						rs.getString(12))
				;

				// inserisco prodotto e quantità
				map.put(prodotto, rs.getInt(11));
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {

			IO.println("CarrelloDAO.doLoad: errore nel recupero degli articoli!\n\n" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}
	
	/** 
	 * Agginge nel DB la relazione tra prodotto e carrello 
	 * @throws SQLException 
	 * */
	public static void doAddItem(Carrello cart, Prodotto item, int quantita) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(ADD_ITEM_CART);
		pstmt.setInt(1, cart.getIdCarrello());
		pstmt.setInt(2, item.getIdProdotto());
		pstmt.setInt(3, quantita);

		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
		
		CarrelloDAO.doUpdate(cart);
	}
	
	/** 
	 * rimuove la relazione tra prodotto e carrello 
	 * @throws SQLException 
	 */
	public static void doDeleteItem(Carrello cart, Prodotto item) throws SQLException  {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(DELETE_ITEM_CART);
		pstmt.setInt(1, item.getIdProdotto());
		pstmt.setInt(2, cart.getIdCarrello());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
		
		CarrelloDAO.doUpdate(cart);
	}
	
	/**
	 * Aggiorna la quantità associata al prodotto presente nel carrello
	 * @throws SQLException 
	 */
	public static void doUpdateItemQuantity(Carrello cart, Prodotto item, int quantity) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(UPDATE_ITEM_QUANTITY);
		pstmt.setInt(1, quantity);
		pstmt.setInt(2, item.getIdProdotto());
		pstmt.setInt(3, cart.getIdCarrello());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
		
		CarrelloDAO.doUpdate(cart);
	}
	
	/**
	 * Svuota il carrello, cancellando tutte le relazioni con gli articoli
	 * @throws SQLException 
	 */
	public static void doFlush(Carrello cart) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(FLUSH_CART);
		pstmt.setInt(1, cart.getIdCarrello());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close();
	}
	
	// -----------------------------------------------------------------------------
	// -- DCS
	// -----------------------------------------------------------------------------
	private static Carrello converti(ResultSet rs) throws SQLException {
		Carrello c = null;
		if (rs.next())
			c = new Carrello(rs.getInt(1), rs.getInt(2), rs.getFloat(3), UtenteDAO.doRetrieveByKey(rs.getString(4)));

		return c;
	}
	// -----------------------------------------------------------------------------

	private static final String FIND_BY_ID = "SELECT * FROM carrello WHERE idCarrello = ?";
	private static final String LOAD_CART_BY_EMAIL = "SELECT * FROM Carrello WHERE Utente = ?";
	private static final String CREATE_CART = "INSERT INTO Carrello (QtaProdotti, PrezzoTotale, Utente) VALUES (0,0,?)";
	private static final String UPDATE = "UPDATE Carrello SET QtaProdotti = ?, PrezzoTotale = ?  WHERE idCarrello = ?"; // FIXATA!!!!

	// -- query per gli articoli
	private final static String ADD_ITEM_CART = "INSERT INTO Inserire VALUES (?,?,?)";
	private final static String UPDATE_ITEM_QUANTITY = "UPDATE Inserire SET Quantita = ? WHERE Prodotto = ? AND Carrello = ?";
	private final static String LOAD_CART_ITEMS = "SELECT P.idProdotto, P.Nome, P.Produttore, P.Piattaforma, P.Genere, P.Descrizione, P.Immagini, P.Prezzo, P.Disponibilita, P.DataUscita, I.Quantita, P.LinkVideo from Prodotto P JOIN Inserire I ON (I.Prodotto=P.idProdotto) WHERE I.Carrello = ?";
	private final static String DELETE_ITEM_CART = "DELETE FROM Inserire WHERE Prodotto = ? AND Carrello = ?";
	private final static String FLUSH_CART = "DELETE FROM Inserire WHERE CARRELLO = ?";
}
