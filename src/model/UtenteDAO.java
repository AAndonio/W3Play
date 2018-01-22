package model;

import java.sql.*;

import bean.Utente;
import utils.DBConnection;

public class UtenteDAO {

	private UtenteDAO() {
	}

	
	public static void doSave(Utente u) throws SQLException {
		String sql = "INSERT INTO Utente VALUES (?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, u.getEmail());
		pstmt.setString(2, u.getNome());
		pstmt.setString(3, u.getCognome());
		pstmt.setString(4, u.getPassword());
		pstmt.setString(5, u.getVia());
		pstmt.setString(6, u.getCivico());
		pstmt.setString(7, u.getCap());
		pstmt.setString(8, u.getCitta());

		pstmt.executeUpdate();

		pstmt.close();
		con.close();

	}

	public static Utente doRetrieveByKey(String email) throws SQLException {
		String sql = "SELECT * FROM utente WHERE Email = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, email);
		ResultSet rs = pstmt.executeQuery();

		return converti(rs);

	}


	
	public static Utente Check(String email, String password) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(CHECK_USER);
		pstmt.setString(1, email);
		pstmt.setString(2, password);

		Utente user = new Utente();

		ResultSet rset = pstmt.executeQuery();
		if (rset.next()) {
			user.setEmail(rset.getString("Email"));
			user.setNome(rset.getString("Nome"));
			user.setCognome(rset.getString("Cognome"));	
			user.setPassword(rset.getString("Password"));
			user.setCap(rset.getString("CAP"));
			user.setCivico(rset.getString("Civico"));
			user.setVia(rset.getString("Via"));
			user.setCitta(rset.getString("Citta"));

			pstmt.close();
			con.close();

			return user;
		} else {
			pstmt.close();
			con.close();

			return user;
		}
	}

/*	public static void doSaveOrUpdate(Utente u) throws SQLException {
		String sql = "INSERT INTO utente VALUES (?,?,?,?,?)";
		String sqlU = "UPDATE utente SET email = ?, nome = ?, cognome = ? , password = ?, indirizzo ) ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		if (doRetrieveByKey(u.getEmail()) == null) {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getEmail());
			pstmt.setString(2, u.getNome());
			pstmt.setString(3, u.getCognome());
			pstmt.setString(4, u.getPassword());
			pstmt.setString(5, u.getVia());
			pstmt.setString(6, u.getCivico());
			pstmt.setString(7, u.getCap());
			pstmt.setString(8, u.getCitta());
			

			pstmt.executeUpdate();
		} else {
			pstmt = con.prepareStatement(sqlU);
			pstmt.setString(1, u.getEmail());
			pstmt.setString(2, u.getNome());
			pstmt.setString(3, u.getCognome());
			pstmt.setString(4, u.getPassword());
			pstmt.setString(5, u.getVia());
			pstmt.setString(6, u.getCivico());
			pstmt.setString(7, u.getCap());
			pstmt.setString(8, u.getCitta());

			pstmt.executeUpdate();
		}

		pstmt.close();
		con.close();
	}
*/
	
	public static void doUpdateAddress(String via, String numeroCivico, String cap, String citta, String utente)
			throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(UPDATE_ADDRESS);
		pstmt.setString(1, via);
		pstmt.setString(2, numeroCivico);
		pstmt.setString(3, cap);
		pstmt.setString(4, citta);
		pstmt.setString(5, utente);

		pstmt.executeUpdate();

		pstmt.close();
		con.close();
	}

	public static boolean checkEmail(String email) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(CHECK_EMAIL);
		pstmt.setString(1, email);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {

			pstmt.close();
			con.close();
			return true;
		} else {

			pstmt.close();
			con.close();
			return false;
		}
	}

	public static void doUpdatePassword(String password, Utente utente) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(UPDATE_PASSWORD);
		pstmt.setString(1, password);
		pstmt.setString(2, utente.getEmail());

		pstmt.executeUpdate();

		pstmt.close();
		con.close();
	}
	
	public static void doUpdateEmail(String newEmail, Utente utente) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(UPDATE_EMAIL);
		pstmt.setString(1, newEmail);
		pstmt.setString(2, utente.getEmail());

		pstmt.executeUpdate();

		pstmt.close();
		con.close();
	}
	public static void doDeleteUser(String email) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(DELETE_USER);
		pstmt.setString(1, email);
	

		pstmt.executeUpdate();

		pstmt.close();
		con.close();
	}
	
	// -------------------------------------------------------------------------------------
	// -- DCS
	// -------------------------------------------------------------------------------------
	public static Utente converti(ResultSet rs) throws SQLException
	{
		Utente u = null;
		if (rs.next())
			 u = new Utente (rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
		
		return u;
	}
	// -------------------------------------------------------------------------------------

	private static final String UPDATE_PASSWORD = "UPDATE Utente SET Password = ? WHERE Email = ?";
	private static final String UPDATE_EMAIL = "UPDATE Utente SET Email = ? WHERE Email = ?";
	private static final String CHECK_EMAIL = "SELECT * FROM Utente WHERE Email = ?";
	private static final String UPDATE_ADDRESS = "UPDATE Utente SET Via = ?, Civico = ?, Cap = ?, Citta = ? WHERE Email = ?";
	private static final String CHECK_USER = "SELECT * FROM Utente WHERE Email = ? AND Password = ?";
	private static final String DELETE_USER= "DELETE FROM Utente WHERE Email = ?";
}
