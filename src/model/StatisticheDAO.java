package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Statistiche;
import utils.DBConnection;

public class StatisticheDAO {

	public StatisticheDAO() {}
	
	public static Statistiche doCalculate() throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs;
		
		con = DBConnection.getConnection();
		
		//numero prodotti venduti
		pstmt = con.prepareStatement(N_PRODOTTI_VENDUTI);
		rs = pstmt.executeQuery();
		
		int nProdottiVenduti = 0;
		if (rs.next())
			nProdottiVenduti = rs.getInt(1);
		
		//numero prodotti catalogo
		pstmt = con.prepareStatement(N_PRODOTTI_CATALOGO);
		rs = pstmt.executeQuery();
		
		int nProdottiCatalogo = 0;
		if (rs.next())
			nProdottiCatalogo = rs.getInt(1);
		
		//numero utenti registrati
		pstmt = con.prepareStatement(N_UTENTI_REGISTRATI);
		rs = pstmt.executeQuery();
		
		int nUtentiRegistrati = 0;
		if (rs.next()) 
			nUtentiRegistrati = rs.getInt(1);
		
		//ordini in media per utente
		int avgOrdiniUtente = 0;
		try {
			
			avgOrdiniUtente = nProdottiVenduti / nUtentiRegistrati;
			
		} catch (Exception e) {
			System.err.println("divisione per 0");
		}
		
		//guadagni ultimo mese
		pstmt = con.prepareStatement(EARNINGS_LAST_MONTH);
		rs = pstmt.executeQuery();
		
		float guadagniUltimoMese = 0f;
		if (rs.next())
			guadagniUltimoMese = rs.getFloat(1);
			
		//
		Statistiche stats = new Statistiche(
				nProdottiCatalogo, 
				nProdottiVenduti, 
				nUtentiRegistrati, 
				guadagniUltimoMese, 
				avgOrdiniUtente)
		; 
		
		return stats;
	}
	
	//-- query:
	private static final String N_PRODOTTI_VENDUTI = "SELECT COUNT(*) FROM dettagliordine";
	private static final String N_PRODOTTI_CATALOGO = "SELECT COUNT(*) FROM prodotto";
	private static final String N_UTENTI_REGISTRATI = "SELECT COUNT(*) FROM utente u WHERE u.Email NOT IN (SELECT a.Email FROM amministratore a)";
	private static final String EARNINGS_LAST_MONTH = "SELECT SUM(o.Prezzo) FROM ordine o WHERE (o.DataAcquisto >= (NOW() - INTERVAL 1 MONTH))";
}
