package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Carrello;
import utils.DBConnection;

public class CarrelloDAO {

	private CarrelloDAO (){}
	
	
	public static void doSave(Carrello c) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(UPDATE);
		pstmt.setInt(1, c.getQtaprodotti());
		pstmt.setFloat(2, c.getPrezzoTotale());
		
		//fix del fix: parametri sbagliati e query update sbagliata >.<
		pstmt.setInt(3, c.getIdCarrello());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
			
	}
	
	public static void doCreate(int qtaProdotti, float prezzoTotale, String email) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(CREATE_CART);
		pstmt.setInt(1, qtaProdotti);
		pstmt.setFloat(2, prezzoTotale);
		pstmt.setString(3, email);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
			
	}
	
	public static Carrello doRetrieveByKey(int idCarrello) throws SQLException
	{
		String sql = "SELECT * FROM carrello WHERE idCarrello = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, idCarrello);
		ResultSet rs = pstmt.executeQuery();
		
		//fix: operation not allowed after result set is closed
		Carrello carrello = converti(rs); 
		
		rs.close();
		pstmt.close();
		con.close(); 		
		
		return carrello; 
			
	}
	
	public static Carrello doRetrieveByUser(String email) throws SQLException
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(LOAD_CART_BY_EMAIL);
		pstmt.setString(1, email);
		ResultSet rs = pstmt.executeQuery();
		
		//fix: operation not allowed after result set is closed
		Carrello carrello = converti(rs); 
		
		pstmt.close();
		con.close(); 	
		
		return carrello;	
	}
	
	//-----------------------------------------------------------------------------
	//-- DCS 
	//-----------------------------------------------------------------------------
	private static Carrello converti(ResultSet rs) throws SQLException
	{
		Carrello c = null;
		if(rs.next())
			 c = new Carrello (rs.getInt(1),rs.getInt(2),rs.getFloat(3),rs.getString(4));
	
		return c;
	}
	//-----------------------------------------------------------------------------
	
	private static final String LOAD_CART_BY_EMAIL = "SELECT * FROM Carrello WHERE Utente = ?";
	private static final String CREATE_CART ="INSERT INTO Carrello (QtaProdotti, PrezzoTotale, Utente) VALUES (?,?,?)";
	private static final String UPDATE ="UPDATE Carrello SET QtaProdotti = ?, PrezzoTotale = ?  WHERE idCarrello = ?"; //FIXATA!!!!
}
