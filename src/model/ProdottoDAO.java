package model;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
 
import search.Findable;
import search.SearchEngine;
import bean.Prodotto;
import util.IO;
import utils.DBConnection;
 
public class ProdottoDAO {
 
    private ProdottoDAO(){}
   
    public static void doSave(Prodotto p)  {
    	
    	
    	
        Connection con = null;
        PreparedStatement pstmt = null;
        
        try {
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(DO_SAVE);
       
        Date date = Date.valueOf(p.getDataUscita());
 
        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getProduttore());
        pstmt.setString(3, p.getPiattaforma());
        pstmt.setString(4, p.getGenere());
        pstmt.setString(5, p.getDescrizione());
        pstmt.setString(6, p.getImmagine());
        pstmt.setFloat(7, p.getPrezzo());
        pstmt.setInt(8, p.getDisponibilita());
        pstmt.setDate(9, date);
        pstmt.setInt(10, 0);
        pstmt.setString(11, p.getLinkVideo());
       
        pstmt.executeUpdate();
        pstmt.close();
        con.close();
        
        
        
        }catch(Exception e) {
        	           
    }
    }
    public static void doUpdate(Prodotto p) 
    {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(DO_UPDATE);
       
        Date date = Date.valueOf(p.getDataUscita());
 
        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getProduttore());
        pstmt.setString(3, p.getPiattaforma());
        pstmt.setString(4, p.getGenere());
        pstmt.setString(5, p.getDescrizione());
        pstmt.setString(6, p.getImmagine());
        pstmt.setFloat(7, p.getPrezzo());
        pstmt.setInt(8, p.getDisponibilita());
        pstmt.setDate(9, date);
        pstmt.setInt(10, 0);
        pstmt.setString(11, p.getLinkVideo());
        pstmt.setInt(12, p.getIdProdotto());
       
        pstmt.executeUpdate();
       
        pstmt.close();
        con.close();
        }catch(Exception e){
        	
        }
           
    }
   
    public static Prodotto doRetrieveByKey(int idProdotto) 
    {
        String sql = "SELECT * FROM prodotto WHERE idProdotto = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, idProdotto);
	        ResultSet rs = pstmt.executeQuery();	        
	        Prodotto px=converti(rs);
	      	        
	        pstmt.close();
	        con.close(); 
	        return px;
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    
           
    }
      
    //SEARCH
    public static ArrayList<Prodotto> search(String query) throws SQLException{
       
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(RETRIVE_ALL);
       
        //retrive products
        //-------------------------------------------------------------------------
        ResultSet rs = pstmt.executeQuery();
       
        List<Findable> list = new ArrayList<>();  //List<Prodotto>
       
        try {
 
            while (rs.next()) {
 
                Prodotto p = new Prodotto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getFloat(8), rs.getInt(9), rs.getDate(10).toLocalDate(), rs.getString(12));
                list.add(p);
            }
 
        } catch (Exception e) {
            IO.err("ERROR", e.getMessage());
        }
        //-------------------------------------------------------------------------
       
        //search
        //-------------------------------------------------------------------------
        SearchEngine se = SearchEngine.getInstance();
       
        System.out.println("searching...");
       
        List<Findable> risultati = se.search(query, list);
        //-------------------------------------------------------------------------
       
        pstmt.close();
        con.close();       
       
        ArrayList<Prodotto> prodotti = new ArrayList<>();
        risultati.forEach((f) -> prodotti.add((Prodotto) f));
       
        return prodotti;
    }
   
    public static ArrayList<Prodotto> ultimiArrivi(){
        Connection con = null;
        PreparedStatement pstmt = null;
 
        try {
        
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(ULTIMI_ARRIVI);
        ResultSet rs = pstmt.executeQuery();
       
        ArrayList<Prodotto> risultati = convertiArray(rs);
       
        pstmt.close();
        con.close();
       
        return risultati;
        }catch(Exception e) {
        	return null;
        }
       
    }
   
    public static ArrayList<Prodotto> piuVenduti() throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(PIU_VENDUTI);
        ResultSet rs = pstmt.executeQuery();
       
        ArrayList<Prodotto> risultati = convertiArray(rs);
       
        pstmt.close();
        con.close();
       
        return risultati;
       
    }
   
    public static ArrayList<Prodotto> searchFromMenuNav(String ProdOrPiatt, String nome) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(RICERCA_DA_MENU_NAV);
        pstmt.setString(1, "%"+ProdOrPiatt);
        pstmt.setString(2, "%"+ProdOrPiatt+"%");
        pstmt.setString(3, ProdOrPiatt+"%");
        pstmt.setString(4, nome+"%");
        pstmt.setString(5, nome);
        ResultSet rs = pstmt.executeQuery();
       
        ArrayList<Prodotto> risultati = convertiArray(rs);
       
        pstmt.close();
        con.close();       
       
        return risultati;
    }
   
    public static ArrayList<Prodotto> searchFromMenuConsole(String nome) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(RICERCA_DA_MENU_CONSOLE);
        pstmt.setString(1, "%"+nome);
        pstmt.setString(2, "%"+nome+"%");
        pstmt.setString(3, nome+"%");
        pstmt.setString(4, "console");
        ResultSet rs = pstmt.executeQuery();
       
        ArrayList<Prodotto> risultati = convertiArray(rs);
       
        pstmt.close();
        con.close();       
       
        return risultati;
    }
   
    public static ArrayList<Prodotto> searchFromMenuGiochi(String ProdOrPiatt) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(RICERCA_DA_MENU_GIOCHI);
        pstmt.setString(1, "%"+ProdOrPiatt);
        pstmt.setString(2, "%"+ProdOrPiatt+"%");
        pstmt.setString(3, ProdOrPiatt+"%");
 
        ResultSet rs = pstmt.executeQuery();
       
        ArrayList<Prodotto> risultati = convertiArray(rs);
       
        pstmt.close();
        con.close();       
       
        return risultati;
    }
   
    public static void vendi(Prodotto prod) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(UPDATE_QUANTITY);
        pstmt.setInt(1, prod.getDisponibilita());
        pstmt.setInt(2, prod.getVenduti());
        pstmt.setInt(3, prod.getIdProdotto());
 
        pstmt.executeUpdate();
       
        pstmt.close();
        con.close();         
    }
   
    public static void removeProduct(int idProd) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        con = DBConnection.getConnection();
       
        pstmt = con.prepareStatement(REMOVE_PRODUCT);
        pstmt.setInt(1, -1); //setta la disponibilita a -1
        pstmt.setInt(2, idProd);
       
        pstmt.executeUpdate();
       
        pstmt.close();
        con.close();
       
    }

   
    //-------------------------------------------------------------------------------------
    //-- DCS
    //-------------------------------------------------------------------------------------
    public static Prodotto converti(ResultSet rs){
        Prodotto p  = null;
        try {
        if(rs.next())
            p = new Prodotto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getFloat(8), rs.getInt(9), rs.getDate(10).toLocalDate(), rs.getString(12));
        }catch(Exception e) {
        	
        }finally {
        	return p;
        }
    }
   
    public static ArrayList<Prodotto> convertiArray(ResultSet rs) throws SQLException
    {
        ArrayList<Prodotto> risultati = new ArrayList<Prodotto>();
       
        while(rs.next()) {
            Prodotto p = new Prodotto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getFloat(8), rs.getInt(9), rs.getDate(10).toLocalDate(), rs.getString(12));
            risultati.add(p);
        }
        return risultati;
    }
    //-------------------------------------------------------------------------------------
 
    private static final String DO_UPDATE= "UPDATE Prodotto SET Nome = ?, Produttore = ?, Piattaforma= ? , Genere= ? , Descrizione= ? , Immagini= ?, Prezzo= ? , Disponibilita= ?, DataUscita= ?, numVenduti= ?, linkVideo= ? WHERE idProdotto = ?";
    private static final String RICERCA_DA_MENU_CONSOLE = "SELECT * FROM Prodotto WHERE Disponibilita>=0 AND (Nome LIKE ? OR Nome LIKE ? OR Nome LIKE ?"
            + "AND Genere = ?)";
    private static final String RICERCA_DA_MENU_NAV = "SELECT * FROM Prodotto WHERE Disponibilita>=0 AND (Produttore LIKE ? OR Produttore LIKE ? OR Produttore LIKE ?"
            + "OR Piattaforma LIKE ? OR Piattaforma = ? )";
    private static final String RICERCA_DA_MENU_GIOCHI = "SELECT * FROM Prodotto WHERE Disponibilita>=0 AND (Piattaforma LIKE ? OR Piattaforma LIKE ? OR Piattaforma LIKE ?)";
    private static final String PIU_VENDUTI = "SELECT * FROM Prodotto WHERE Disponibilita>=0 ORDER BY numVenduti DESC LIMIT 0,5";
    private static final String ULTIMI_ARRIVI = "SELECT * FROM Prodotto WHERE Disponibilita>=0 ORDER BY DataUscita DESC LIMIT 0,5";
    private static final String LOAD_BY_NAME = "SELECT * FROM Prodotto WHERE Nome LIKE ? OR Nome LIKE ? OR Nome LIKE ?";
    private static final String DO_SAVE ="INSERT INTO Prodotto(Nome, Produttore, Piattaforma, Genere, Descrizione, Immagini, Prezzo, Disponibilita, DataUscita, numVenduti, linkVideo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUANTITY="UPDATE Prodotto SET Disponibilita = ?, numVenduti = ? WHERE idProdotto = ?";
    private static final String REMOVE_PRODUCT="UPDATE Prodotto SET Disponibilita = ? WHERE idProdotto = ?";
    private static final String RETRIVE_ALL = "SELECT * FROM prodotto p WHERE Disponibilita>=0";
}