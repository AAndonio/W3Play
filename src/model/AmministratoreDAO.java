package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Amministratore;
import bean.Utente;
import utils.DBConnection;
import utils.Schema;

public class AmministratoreDAO {
	
	public static Amministratore controllaCredenziali(String email, String password) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(CHECK_ADMIN);
		pstmt.setString(1, email);

		Amministratore admin = new Amministratore();
		
		//controlla prima se esiste un amministratore con l'email specificata
		ResultSet rset = pstmt.executeQuery();
		if (rset.next()) {
			
			//poi controlla se cè un account utente (email, password)
			PreparedStatement ps = con.prepareStatement(GET_UTENTE);
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				//
				Utente utente = new Utente(
					rs.getString(Schema.Utente.EMAIL), 
					rs.getString(Schema.Utente.NOME),
					rs.getString(Schema.Utente.COGNOME),
					rs.getString(Schema.Utente.PASSWORD),
					rs.getString(Schema.Utente.VIA), 
					rs.getString(Schema.Utente.CIVICO),
					rs.getString(Schema.Utente.CAP),
					rs.getString(Schema.Utente.CITTA))
				;
				
				//
				admin = new Amministratore(
						utente, 
						Amministratore.Ruolo.valueOf(rset.getString(Schema.Amministratore.RUOLO)))
				;
				
				rs.close();
				ps.close();
				rset.close();
				pstmt.close();
				con.close();
				
			} else {
				throw new SQLException("l'amministratore non ha un account utente!");
			}

			return admin;
			
		} else {
			pstmt.close();
			con.close();

			return admin;
		}
	}
	
	public static void doUpdatePassword(String password, Amministratore amministratore) throws SQLException {

		amministratore.getUtente().aggiornaPassword(password);
	}
	
	public static void doUpdateEmail(String newEmail, Amministratore amministratore) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();
		
		pstmt = con.prepareStatement(UPDATE_EMAIL);
		pstmt.setString(1, newEmail);
		pstmt.setString(2, amministratore.getEmail());
		
		//aggiorna email nella tabella amministratore
		pstmt.executeUpdate();
		
		//aggiorna l'email nella tabella utente
		amministratore.getUtente().aggiornaEmail(newEmail);
		
		pstmt.close();
		con.close();
	}
	
	public static void doDeleteAdmin(String email) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		con = DBConnection.getConnection();

		pstmt = con.prepareStatement(DELETE_ADMIN);
		pstmt.setString(1, email);
	

		pstmt.executeUpdate();

		pstmt.close();
		con.close();
	}
	
	//TODO: fare update ruolo
	
	//-- query
	private static final String UPDATE_EMAIL = "UPDATE amministratore SET Email = ? WHERE Email = ?";
	private static final String CHECK_ADMIN = "SELECT * FROM amministratore WHERE Email = ?";
	private static final String GET_UTENTE = "SELECT * FROM utente WHERE Email = ? AND Password = ?";
	private static final String DELETE_ADMIN= "DELETE FROM amministratore WHERE Email = ?";

}
