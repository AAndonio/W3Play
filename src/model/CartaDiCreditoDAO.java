package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.CartaDiCredito;
import utils.DBConnection;

public class CartaDiCreditoDAO {

	private CartaDiCreditoDAO() {}
	
	public static void doSave(CartaDiCredito c) throws SQLException
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		Date date = Date.valueOf(c.getScadenza());
		
		pstmt = con.prepareStatement(DO_SAVE);
		pstmt.setString(1, c.getNumerocarta());
		pstmt.setString(2, c.getTitolare());
		pstmt.setObject(3, date);
		pstmt.setInt(4, c.getCCV());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
	}
	
	public static CartaDiCredito doRetrieveByKey(String numcarta) throws SQLException
	{
		String sql = "SELECT * FROM cartadicredito WHERE numerocarta = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, numcarta);
		ResultSet rs = pstmt.executeQuery();
		
		pstmt.close();
		con.close(); 		
		
		return converti(rs);	
	}
	
	public static ArrayList<CartaDiCredito> doRetrieveByUser(String email) throws SQLException
	{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(LOAD_CARD_BY_EMAIL);
		pstmt.setString(1, email);
		ResultSet rs = pstmt.executeQuery();
		
		ArrayList<CartaDiCredito> carte = convertiArray(rs); 
		
		pstmt.close();
		con.close(); 	
		
		return carte;
			
	}
	
	public static void doUpdate(String vecchia, CartaDiCredito nuova) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		Date date = Date.valueOf(nuova.getScadenza());
		
		pstmt = con.prepareStatement(UPDATE_CREDIT_CARD);
		pstmt.setString(1, nuova.getNumerocarta());
		pstmt.setString(2, nuova.getTitolare());
		pstmt.setDate(3, date);
		pstmt.setInt(4, nuova.getCCV());
		pstmt.setString(5, vecchia);
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 	
	}
	
	//-----------------------------------------------------------------------------
	//-- DCS 
	//-----------------------------------------------------------------------------
	public static CartaDiCredito converti(ResultSet rs) throws SQLException
	{
		CartaDiCredito c = null;
		if(rs.next())
			 c = new CartaDiCredito (rs.getString(1),rs.getString(2),rs.getDate(3).toLocalDate(),rs.getInt(4));
	
		return c;
	}
	
	public static ArrayList<CartaDiCredito> convertiArray(ResultSet rs) throws SQLException
	{
		ArrayList<CartaDiCredito> carte = new ArrayList<CartaDiCredito>();
		
		while(rs.next()) {
			carte.add(new CartaDiCredito (rs.getString(1),rs.getString(2),rs.getDate(3).toLocalDate(),rs.getInt(4)));
		}
		
		return carte;
	}
	
	//Metodi di associazione tabelle Utente-Carta
	
    public static void doSave(String carta, String utente) throws SQLException
    {
       
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(SAVE);
        pstmt.setString(1, utente);
        pstmt.setString(2, carta);
       
        pstmt.executeUpdate();
       
        pstmt.close();
        con.close();
           
    }
   
    public static void doDelete(String carta, String utente) throws SQLException
    {
   
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(DELETE);
        pstmt.setString(1, carta);
        pstmt.setString(2, utente);
       
        pstmt.executeUpdate();
       
        pstmt.close();
        con.close();
           
    }
	
	
	//-----------------------------------------------------------------------------
	
	private static final String DO_SAVE = "INSERT INTO CartaDiCredito VALUES (?,?,?,?)";
	private static final String LOAD_CARD_BY_EMAIL = "SELECT * FROM CartaDiCredito WHERE NumeroCarta IN (SELECT Carta FROM Associare WHERE Utente = ?)";
	private static final String UPDATE_CREDIT_CARD = "UPDATE CartaDiCredito SET NumeroCarta=?,Titolare=?,Scadenza=?,CCV=? WHERE NumeroCarta = ?";
	
	private static final String SAVE ="INSERT INTO Associare VALUES (?,?)";
    private static final String DELETE ="DELETE FROM Associare WHERE Carta = ? AND Utente = ?";

}
