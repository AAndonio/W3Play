package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.Ordine;
import bean.Prodotto;
import bean.Utente;
import utils.DBConnection;

public class OrdineDAO {

	private OrdineDAO() {}
	
	/**
	 * Salva l'ordine nel DB, salvando anche le relazioni tra ordine e prodotto
	 * @throws SQLException
	 */
	public static int inoltraOrdine(Ordine ordine) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		//data di acquisto dell'ordine
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
		
		pstmt = con.prepareStatement(LOAD_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
		pstmt.setFloat(1, ordine.getCosto());
		pstmt.setObject(2, timestamp);
		pstmt.setString(3, ordine.getCarta());
		pstmt.setString(4, ordine.getUtente());

		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		int id = estraiID(rs);
		
		if (id == 0)
			throw new SQLException("OrdineDAO: id ordine non valido!");
		else
			ordine.setIdOrdine(id);
		
		rs.close();
		pstmt.close();
		
		//salva le relazioni tra utente-ordine con prodotto
		List<Prodotto> articoli = ordine.getArticoli();
		
		if (articoli != null) {
			
			for (Prodotto articolo: articoli) {
				
				pstmt = con.prepareStatement(ADD_ARTICOLO);
				pstmt.setInt(1, articolo.getIdProdotto());
				pstmt.setInt(2, id);
				
				pstmt.executeUpdate();
				pstmt.close();
			}
		}
		
		con.close();

		return id;
	}
	
	/**
	 * Recupera tutti gli ordini effettuati da un utente, con i relativi prodotti acquistati
	 * @throws SQLException
	 */
	public static ArrayList<Ordine> doRetrieveByUtente(Utente utente) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(SEARCH_ORDINI);
		pstmt.setString(1, utente.getEmail());
		ResultSet rs = pstmt.executeQuery();

		ArrayList<Ordine> or = convertiArrayList(rs);
		
		rs.close();
		pstmt.close();
		
		//recupero prodotti per ordine
		for (Ordine ordine: or) {
			
			List<Prodotto> articoli = new ArrayList<>();
			
			pstmt = con.prepareStatement(GET_ID_ARTICOLI);
			pstmt.setInt(1, ordine.getIdOrdine());
			
			ResultSet rst = pstmt.executeQuery();
			
			while (rst.next())
				articoli.add(ProdottoDAO.doRetrieveByKey(rst.getInt(1)));
			
			rs.close();
			
			ordine.setArticoli(articoli);
		}
		
		pstmt.close();
		con.close();

		return or;
	}

	// -------------------------------------------------------------------------------------
	// -- DCS
	// -------------------------------------------------------------------------------------
	private static int estraiID(ResultSet rs) throws SQLException {
		int id = 0;
		if (rs.next()) {
			id = rs.getInt(1);
		}
		return id;
	}

	private static ArrayList<Ordine> convertiArrayList(ResultSet rs) throws SQLException {
		ArrayList<Ordine> o = new ArrayList<Ordine>();
		while (rs.next()) {
			o.add(new Ordine(rs.getInt(1), rs.getFloat(2), rs.getDate(3).toLocalDate(), rs.getString(4),
					rs.getString(5)));
		}
		return o;
	}
	// -------------------------------------------------------------------------------------
	
	private static final String FIND_BY_ID = "SELECT * FROM ordine WHERE idOrdine = ?";
	private static final String SEARCH_ORDINI = "SELECT * FROM Ordine WHERE Utente = ?";
	private static final String LOAD_ORDER = "INSERT INTO Ordine (Prezzo, DataAcquisto, Carta, Utente) VALUES (?,?,?,?)";
	private static final String ADD_ARTICOLO = "INSERT INTO dettagliordine VALUES (?,?,1)"; //quantità fissa a 1 xke non è usata nel repilogo ordine
	private static final String GET_ID_ARTICOLI = "SELECT prodotto FROM dettagliordine WHERE ordine = ?";
}
