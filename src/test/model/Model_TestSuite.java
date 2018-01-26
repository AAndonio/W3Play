package test.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	AmministratoreDAO_Test.class, 
	CarrelloDAO_Test.class, 
	CartaDiCreditoDAO_Test.class,
	StatisticheDAO_Test.class,
	TestOrdineDAO.class,
	TestUtenteDAO.class,
	TestProdottoDAO.class
})
public class Model_TestSuite {

}
