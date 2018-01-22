package test.DAO;



import java.sql.SQLException;

import org.junit.*;

import bean.Utente;
import model.UtenteDAO;


public class TestUtenteDAO {

	private static Utente user=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		 user = new Utente("prova@prova.it","provanome","provacognome","prova123","via prova","1","84091","Battipaglia");
	}
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void TestDoSave() {
		try {
		UtenteDAO.doSave(user);
		Utente u = UtenteDAO.doRetrieveByKey(user.getEmail());
		Assert.assertEquals("prova@prova.it", u.getEmail());
		Assert.assertEquals("provanome", u.getNome());
		Assert.assertEquals("provacognome", u.getCognome());
		Assert.assertEquals("prova123", u.getPassword());
		Assert.assertEquals("via prova", u.getVia());
		Assert.assertEquals("1", u.getCivico());
		Assert.assertEquals("84091", u.getCap());
		Assert.assertEquals("Battipaglia", u.getCitta());
		
		}catch(SQLException e) {
			
		}	
	}
	
	@Test
	public void TestDoRetrieveByKey() {
		
		try {
		
		Utente u = UtenteDAO.doRetrieveByKey(user.getEmail());
		Assert.assertEquals(user.getEmail(), u.getEmail());
		Assert.assertEquals(user.getNome(), u.getNome());
		Assert.assertEquals(user.getCognome(), u.getCognome());
		Assert.assertEquals(user.getPassword(), u.getPassword());
		Assert.assertEquals(user.getVia(), u.getVia());
		Assert.assertEquals(user.getCivico(), u.getCivico());
		Assert.assertEquals(user.getCap(), u.getCap());
		Assert.assertEquals(user.getCitta(), u.getCitta());
		
		}catch(SQLException e) {
			
		}	
	}
	

	@Test
	public void TestCheck() {
		
		try {
		
		Utente u = UtenteDAO.Check(user.getEmail(),user.getPassword());
		Assert.assertEquals(user.getEmail(), u.getEmail());
		Assert.assertEquals(user.getNome(), u.getNome());
		Assert.assertEquals(user.getCognome(), u.getCognome());
		Assert.assertEquals(user.getPassword(), u.getPassword());
		Assert.assertEquals(user.getVia(), u.getVia());
		Assert.assertEquals(user.getCivico(), u.getCivico());
		Assert.assertEquals(user.getCap(), u.getCap());
		Assert.assertEquals(user.getCitta(), u.getCitta());
		
		}catch(SQLException e) {
			
		}	
	}
	
	@Test
	public void TestDoUpdatePassword() {
		
		try {
		
		
		UtenteDAO.doUpdatePassword("prova321", user);
		user.setPassword("prova321");
		Utente u = UtenteDAO.doRetrieveByKey(user.getEmail());
		Assert.assertEquals(user.getEmail(), u.getEmail());
		Assert.assertEquals(user.getNome(), u.getNome());
		Assert.assertEquals(user.getCognome(), u.getCognome());
		Assert.assertEquals(user.getPassword(), u.getPassword());
		Assert.assertEquals(user.getVia(), u.getVia());
		Assert.assertEquals(user.getCivico(), u.getCivico());
		Assert.assertEquals(user.getCap(), u.getCap());
		Assert.assertEquals(user.getCitta(), u.getCitta());
		
		}catch(SQLException e) {
			
		}	
	}
	
	@Test
	public void TestDoUpdateIndirizzo() {
		
		try {
		
		UtenteDAO.doUpdateAddress("via roma", "250", "84092", "Bellizzi", user.getEmail());
		user.setVia("via roma");user.setCap("84092");user.setCitta("Bellizzi");user.setCivico("250");
		Utente u = UtenteDAO.doRetrieveByKey(user.getEmail());
		Assert.assertEquals(user.getEmail(), u.getEmail());
		Assert.assertEquals(user.getNome(), u.getNome());
		Assert.assertEquals(user.getCognome(), u.getCognome());
		Assert.assertEquals(user.getPassword(), u.getPassword());
		Assert.assertEquals(user.getVia(), u.getVia());
		Assert.assertEquals(user.getCivico(), u.getCivico());
		Assert.assertEquals(user.getCap(), u.getCap());
		Assert.assertEquals(user.getCitta(), u.getCitta());
		
		}catch(SQLException e) {
			
		}	
	}
	
	@Test
	public void TestCheckEmail() {
		
		try {
		
		boolean flag;
		flag=UtenteDAO.checkEmail(user.getEmail());	
		Assert.assertEquals(true,flag);
		
		}catch(SQLException e) {
			
		}	
	}
	
	@Test
	public void TestDoUpdateEmail() {
		
		try {
		
		UtenteDAO.doUpdateEmail("prova2@prova.it", user);
		user.setEmail("prova2@prova.it");
		Utente u = UtenteDAO.doRetrieveByKey(user.getEmail());
		Assert.assertEquals(user.getEmail(), u.getEmail());
		Assert.assertEquals(user.getNome(), u.getNome());
		Assert.assertEquals(user.getCognome(), u.getCognome());
		Assert.assertEquals(user.getPassword(), u.getPassword());
		Assert.assertEquals(user.getVia(), u.getVia());
		Assert.assertEquals(user.getCivico(), u.getCivico());
		Assert.assertEquals(user.getCap(), u.getCap());
		Assert.assertEquals(user.getCitta(), u.getCitta());
		
		}catch(SQLException e) {
			
		}	
	}
	
	
	
	@Test
	public void TestDoDeleteUser() {
		
		try {
		
		UtenteDAO.doDeleteUser(user.getEmail());
		Utente u = UtenteDAO.doRetrieveByKey(user.getEmail());
		
		Assert.assertEquals(null,u);
		
		}catch(SQLException e) {
			
		}	
	}
	
	
	

	
	
	@After
	public void tearDown() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() {
		
	}
	

}
