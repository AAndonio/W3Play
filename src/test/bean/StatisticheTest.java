package test.bean;

import org.junit.Test;

import bean.Statistiche;
import util.IO;

public class StatisticheTest {

	@Test
	public void test() {
		
		Statistiche stats = new Statistiche();
		stats.aggiorna();
		
		IO.println(stats);
	}
}
