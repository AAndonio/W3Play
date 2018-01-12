package test.bean;

import org.junit.Test;

import bean.Statistiche;
import util.IO;

/*
 * classe di test per le statistiche del sistema
 */
public class StatisticheTest {

	@Test
	public void test() {
		
		Statistiche stats = new Statistiche();
		stats.aggiorna();
		
		IO.println(stats);
	}
}
