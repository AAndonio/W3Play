package test.model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import bean.Statistiche;
import model.StatisticheDAO;

/**
 * Classe di test per {@link StatisticheDAO}
 * @author Augusto
 */
public class StatisticheDAO_Test {

	@Test
	public void test_doCalculate() throws SQLException {
		
		Statistiche stats = StatisticheDAO.doCalculate();
		
		assertNotNull(stats);
	}

}
