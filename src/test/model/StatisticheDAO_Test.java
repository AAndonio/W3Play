package test.model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import bean.Statistiche;
import model.StatisticheDAO;

/**
 * Classe di test per StatisticheDAO
 * @author Luca
 */
public class StatisticheDAO_Test {

	@Test
	public void test_doCalculate() throws SQLException {
		
		//calcolo delle statistiche dai dati nel db
		Statistiche stats = StatisticheDAO.doCalculate();
		
		assertNotNull(stats);
	}

}
