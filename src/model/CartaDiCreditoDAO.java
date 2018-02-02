package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.CartaDiCredito;
import utils.DBConnection;

/**
 * Classe DAO per {@link CartaDiCredito}
 * @author Alfonso
 */
public class CartaDiCreditoDAO {

	private CartaDiCreditoDAO() {}
	
	/**
	 * Salva la carta di credito nel database
	 * @param c: {@link CartaDiCredito}
	 * @throws SQLException
	 */
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
	
	/**
	 * Recupera una carta di credito in base al suo id (numero di carta)
	 * @param numcarta: id della carta
	 * @return {@link CartaDiCredito}
	 * @throws SQLException
	 */
	public static CartaDiCredito doRetrieveByKey(String numcarta) throws SQLException
	{
		String sql = "SELECT * FROM cartadicredito WHERE numerocarta = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, numcarta);
		ResultSet rs = pstmt.executeQuery();
		
		CartaDiCredito carta = converti(rs);
		
		pstmt.close();
		con.close();
		rs.close();
		
		return carta;	
	}
	
	/**
	 * Recupera tutte le carte di credito di un utente
	 * @param email: email dell'utente
	 * @return ArrayList<CartaDiCredito>: una lista di carte di credito
	 * @throws SQLException
	 */
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
	
	/**
	 * Aggiorna la carta di credito con nuovi dati
	 * @param vecchia: numero di carta della vecchia carta di credito
	 * @param nuova: nuova carta di credito
	 * @throws SQLException
	 */
	public static void doUpdate(String vecchia, CartaDiCredito nuova) throws SQLException {
		
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
	
	/* (non-Javadoc) */
	private static CartaDiCredito converti(ResultSet rs) throws SQLException
	{
		CartaDiCredito c = null;
		if(rs.next())
			 c = new CartaDiCredito (rs.getString(1),rs.getString(2),rs.getDate(3).toLocalDate(),rs.getInt(4));
	
		return c;
	}
	
	/* (non-Javadoc) */
	private static ArrayList<CartaDiCredito> convertiArray(ResultSet rs) throws SQLException
	{
		ArrayList<CartaDiCredito> carte = new ArrayList<CartaDiCredito>();
		
		while(rs.next()) {
			carte.add(new CartaDiCredito (rs.getString(1),rs.getString(2),rs.getDate(3).toLocalDate(),rs.getInt(4)));
		}
		
		return carte;
	}
	
	//Metodi di associazione tabelle Utente-Carta
	
	/**
	 * Associa la carta di credito all'utente
	 * @param carta: numero di carta
	 * @param utente: email dell'utente
	 * @throws SQLException
	 */
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
    
    /**
     * Cancella l'associazione (carta di credito, utente) dal database
     * @param carta: numero di carta
     * @param utente: email dell'utente
     * @throws SQLException
     */
    public static void doDelete(String carta, String utente) throws SQLException
    {
   
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(DELETE);
        pstmt.setString(1, carta);
        pstmt.setString(2, utente);
        
        //cancello associazione con utente
        pstmt.executeUpdate();
        pstmt.close();
        
        //count delle associazioni
        pstmt = con.prepareStatement(COUNT_ASSOCIAZIONI);
        pstmt.setString(1,  carta);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
        	
        	int count = rs.getInt(1);
        	
        	//cancella la carta di credito se count == 0
        	if (count == 0) {
        		
        		PreparedStatement statement = con.prepareStatement(DELETE_CARD);
        		statement.setString(1, carta);
                
                //cancello la carta
        		statement.executeUpdate();
        		statement.close();
        	}
        }
       
        rs.close();
        pstmt.close();
        con.close();
    }
	
	//-----------------------------------------------------------------------------
	
	private static final String DO_SAVE = "INSERT INTO CartaDiCredito VALUES (?,?,?,?)";
	private static final String LOAD_CARD_BY_EMAIL = "SELECT * FROM CartaDiCredito WHERE NumeroCarta IN (SELECT Carta FROM Associare WHERE Utente = ?)";
	private static final String UPDATE_CREDIT_CARD = "UPDATE CartaDiCredito SET NumeroCarta=?,Titolare=?,Scadenza=?,CCV=? WHERE NumeroCarta = ?";
	
	private static final String SAVE ="INSERT INTO Associare VALUES (?,?)";
    private static final String DELETE ="DELETE FROM Associare WHERE Carta = ? AND Utente = ?";
    private static final String DELETE_CARD = "DELETE FROM CartaDiCredito WHERE NumeroCarta = ?";
    private static final String COUNT_ASSOCIAZIONI = "SELECT count(*) FROM associare WHERE Carta = ?";

}
