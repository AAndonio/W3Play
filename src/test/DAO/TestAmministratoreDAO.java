package test.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.*;

import bean.Amministratore;
import bean.Amministratore.Ruolo;
import bean.Prodotto;
import bean.Utente;
import model.AmministratoreDAO;
import model.UtenteDAO;
import utils.DBConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class TestAmministratoreDAO {
	private static final Ruolo Backoffice = null;
	private static Amministratore admin=null;
	private static Utente user=null;
	private static String QRY= "INSERT INTO amministratore (Email, Ruolo) VALUES ('augu96@hotmail.it', 'Backoffice');";
	private static String QRY2= "DELETE FROM amministratore Where Email='augu96@hotmail.it'";
	private static Connection con = null;
    private static Statement stmt = null;
	

	@BeforeClass
	public static void setUpBeforeClass() {
		
		admin=new Amministratore();
		admin.setRuolo(Amministratore.Ruolo.valueOf("Backoffice"));
		try {
			
			 con = DBConnection.getConnection();
		     stmt = con.createStatement();
		     user=UtenteDAO.doRetrieveByKey("augu96@hotmail.it");
		     admin.setUtente(user);
		     stmt.executeUpdate(QRY);
		     stmt.close();
			}catch(Exception e) {
				
			}
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void TestControllaCredenziali() {
		try {
			
			Amministratore nuovo=AmministratoreDAO.controllaCredenziali(admin.getEmail(),admin.getPassword());
			Assert.assertEquals(admin.getEmail(), nuovo.getEmail());
			Assert.assertEquals(admin.getRuolo(), nuovo.getRuolo());
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	@Test
	public void TestdoUpdatePassowrd() {
		try {
			AmministratoreDAO.doUpdatePassword("prova", admin);
			admin.setPassword("prova");
			Amministratore nuovo= AmministratoreDAO.controllaCredenziali(admin.getEmail(), admin.getPassword());
			Assert.assertEquals(admin.getEmail(), nuovo.getEmail());
			Assert.assertEquals(admin.getRuolo(), nuovo.getRuolo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	@Test
	public void TestdoUpdateEmail() {
		try {
			AmministratoreDAO.doUpdateEmail("augu966@hotmail.it", admin);
			admin.setUtente(UtenteDAO.doRetrieveByKey("augu966@hotmail.it"));
			Amministratore nuovo= AmministratoreDAO.controllaCredenziali(admin.getEmail(), admin.getPassword());
			Assert.assertEquals(admin.getEmail(), nuovo.getEmail());
			Assert.assertEquals(admin.getRuolo(), nuovo.getRuolo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	@Test
	public void TestDeleteAdmin() {
		try {
			AmministratoreDAO.doDeleteAdmin(admin.getEmail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	
	
	@After
	public void tearDown() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() {
		
		try {
			AmministratoreDAO.doUpdateEmail("augu96@hotmail.it", admin);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
