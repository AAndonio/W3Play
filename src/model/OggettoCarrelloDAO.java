package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Prodotto;
import utils.DBConnection;

import java.sql.ResultSet;

public class OggettoCarrelloDAO {
	
	private OggettoCarrelloDAO() {}
	
	public static ArrayList<OggettoCarrello> doLoad(int carrello) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(LOAD_CART_ITEMS);
		pstmt.setInt(1, carrello);
		
		ResultSet rs = pstmt.executeQuery();
		
		ArrayList<OggettoCarrello> articoli = convertiArray(rs);
		
		pstmt.close();
		con.close(); 
		
		return articoli;
			
	}
	
	public static void doDeleteItemByIdCart(int prodotto, int carrello) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(DELETE_ITEM_BY_ID_AND_CART);
		pstmt.setInt(1, prodotto);
		pstmt.setInt(2, carrello);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
	}
	
	public static void doDeleteItemByCart(int carrello) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(DELETE_ITEM_BY_CART);
		pstmt.setInt(1, carrello);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
	}
	
	public static void doLoadItemByCart(OggettoCarrello articolo, int carrello) throws SQLException 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(LOAD_ITEM_BY_CART);
		pstmt.setInt(1, carrello);
		pstmt.setInt(2, articolo.getOggetto().getIdProdotto());
		pstmt.setInt(3, articolo.getQuantita());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
	}
	
	public static void doUpdateItemQuantity(int carrello, int prodotto, int quantita) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(UPDATE_ITEM_QUANTITY_BY_CART_AND_PRODUCT);
		pstmt.setInt(1, quantita);
		pstmt.setInt(2, prodotto);
		pstmt.setInt(3, carrello);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.close(); 
	}
	
	//-----------------------------------------------------------------------------
	//-- DCS
	//-----------------------------------------------------------------------------
	public static ArrayList<OggettoCarrello> convertiArray(ResultSet rs) throws SQLException
	{
		ArrayList<OggettoCarrello> articoli = new ArrayList<OggettoCarrello>();
		
		while(rs.next()) 
		{
			Prodotto temp = new Prodotto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getFloat(8), rs.getInt(9), rs.getDate(10).toLocalDate(), rs.getString(12));
			articoli.add(new OggettoCarrello(temp,rs.getInt(11)));
		}
		
		return articoli;
	}
	//-----------------------------------------------------------------------------
	
	private final static String LOAD_ITEM_BY_CART = "INSERT INTO Inserire VALUES (?,?,?)";
	private final static String UPDATE_ITEM_QUANTITY_BY_CART_AND_PRODUCT = "UPDATE Inserire SET Quantita = ? WHERE Prodotto = ? AND Carrello = ?";
	private final static String LOAD_CART_ITEMS = "SELECT P.idProdotto, P.Nome, P.Produttore, P.Piattaforma, P.Genere, P.Descrizione, P.Immagini, P.Prezzo, P.Disponibilita, P.DataUscita, I.Quantita, P.LinkVideo from Prodotto P JOIN Inserire I ON (I.Prodotto=P.idProdotto) WHERE I.Carrello = ?";
	private final static String DELETE_ITEM_BY_ID_AND_CART = "DELETE FROM Inserire WHERE Prodotto = ? AND Carrello = ?";	
	private final static String DELETE_ITEM_BY_CART = "DELETE FROM Inserire WHERE CARRELLO = ?";

}
