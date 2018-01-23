package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Ordine;
import bean.Prodotto;
import utils.DBConnection;

public class OggettoOrdineDAO {
	
	public static void addItem(Ordine ordine) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(ADD_ITEM);
		
		for(OggettoOrdine ogg: ordine.getArticoli() ) {
			pstmt.setInt(1, ordine.getIdOrdine());
			pstmt.setInt(2, ogg.getProdotto().getIdProdotto());
			pstmt.setInt(3, ogg.getQuantita());
			pstmt.executeUpdate();
		}
		pstmt.close();
		con.close(); 		
	}
	
	public static ArrayList<Ordine> recuperaItemAllOrdersOfAnUser(ArrayList<Ordine> ordiniUtente) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(RETRIEVE_ORDER_ITEM);
		
		for(Ordine ordine: ordiniUtente) {
			pstmt.setInt(1, ordine.getIdOrdine());
			ResultSet rs = pstmt.executeQuery();
			ArrayList<OggettoOrdine> temp = convertiArray(rs);
			ordine.setArticoli(temp);
		}
		pstmt.close();
		con.close();
		
		return ordiniUtente;
	}
	
	//-------------------------------------------------------------------------------------
	//-- DCS
	//-------------------------------------------------------------------------------------
	public static ArrayList<OggettoOrdine> convertiArray(ResultSet rs) throws SQLException{
		
		ArrayList<OggettoOrdine> articoli = new ArrayList<OggettoOrdine>();
		
		while(rs.next()) 
		{
			Prodotto temp = new Prodotto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getFloat(8), rs.getInt(9), rs.getDate(10).toLocalDate(), rs.getString(11));
			articoli.add(new OggettoOrdine(temp,rs.getInt(12)));
		}
		
		return articoli;
	}
	//-------------------------------------------------------------------------------------

	private static final String ADD_ITEM="INSERT INTO Processare VALUES (?,?,?)";
	//private static final String RETRIEVE_ORDER_ITEM = "SELECT X.idProdotto, X.Nome, X.Produttore, X.Piattaforma, X.Genere, X.Descrizione, X.Immagini, X.Prezzo,X.Disponibilita, X.DataUscita, X.linkVideo, P.Quantita  FROM Processare P JOIN Prodotto X ON (P.Prodotto = X.idProdotto) WHERE P.Ordine = ?";
	private static final String RETRIEVE_ORDER_ITEM = "SELECT * FROM Processare P JOIN Prodotto X ON (P.Prodotto = X.idProdotto) WHERE P.Ordine = ?";
}
