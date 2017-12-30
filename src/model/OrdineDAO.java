package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Ordine;
import utils.DBConnection;

public class OrdineDAO {

	private OrdineDAO () {}

	
	public static Ordine doRetrieveByKey(int idOrdine) throws SQLException
	{
		String sql = "SELECT * FROM ordine WHERE idOrdine = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, idOrdine);
		ResultSet rs = pstmt.executeQuery();
		
		pstmt.close();
		con.close(); 		
		
		return converti(rs); 
			
	}
	
	public static int inoltraOrdine(Ordine ordine) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		Date date = Date.valueOf(ordine.getDataAcquisto());
		
		pstmt = con.prepareStatement(LOAD_ORDER);
		pstmt.setFloat(1, ordine.getCosto());
		pstmt.setDate(2, date);
		pstmt.setString(3, ordine.getCarta());
		pstmt.setString(4, ordine.getUtente());
		
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		
		int id = estraiID(rs);
		
		pstmt.close();
		con.close(); 
		
		return id;
}
	public static ArrayList<Ordine> doRetrieveByUtente(String utente) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(SEARCH_ORDINI);
		pstmt.setString(1, utente);
		ResultSet rs = pstmt.executeQuery(); 		
		
		ArrayList<Ordine> or = convertiArrayList(rs); 
		
		pstmt.close();
		con.close();
		
		return or;
	}
	
	//-------------------------------------------------------------------------------------
	//-- DCS
	//-------------------------------------------------------------------------------------
	public static Ordine converti(ResultSet rs) throws SQLException
	{
		Ordine o  = new Ordine();
		if(rs.next())
			o = new Ordine(rs.getInt(1),rs.getFloat(2),rs.getDate(3).toLocalDate(),rs.getString(4),rs.getString(5));

		return o;
	}

	public static int estraiID(ResultSet rs) throws SQLException {
		int id = 0;
		if(rs.next()){
			id = rs.getInt(1);
		}
		return id;
	}

	public static ArrayList<Ordine> convertiArrayList(ResultSet rs) throws SQLException
	{
		ArrayList<Ordine> o  = new ArrayList<Ordine>();
		while(rs.next())
		{
			o.add(new Ordine(rs.getInt(1),rs.getFloat(2),rs.getDate(3).toLocalDate(),rs.getString(4),rs.getString(5)));
		}
		return o;
	}
	//-------------------------------------------------------------------------------------
	
	private static final String SEARCH_ORDINI = "SELECT * FROM Ordine WHERE Utente = ?";
	private static final String LOAD_ORDER="INSERT INTO Ordine (Prezzo, DataAcquisto, Carta, Utente) VALUES (?,?,?,?)";
}
