package test.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.*;

import bean.Amministratore;
import bean.Utente;
import model.AmministratoreDAO;
import model.UtenteDAO;
import utils.DBConnection;

public class AmministratoreDAO_Test {

	private static Amministratore admin = null;
	private static Utente user = null;
	private static String QRY = "INSERT INTO amministratore (Email, Ruolo) VALUES (?, ?);";
	private static String DELETE = "DELETE FROM amministratore Where Email = ?";
	private static Connection con = null;
	private static PreparedStatement stmt = null;

	@Before
	public void setUp() throws SQLException {
		
		// utente di test
		user = new Utente("test@email.it", "Nome", "Cognome", "testprova", "via", "12", "84090", "city");
		UtenteDAO.doSave(user);
		
		//admin test
		admin = new Amministratore(user, Amministratore.Ruolo.Business);

		con = DBConnection.getConnection();
		stmt = con.prepareStatement(QRY);
		stmt.setString(1, admin.getEmail());
		stmt.setString(2, admin.getRuolo().toString());
		stmt.executeUpdate();
		stmt.close();
		con.close();
	}

	@Test
	public void TestControllaCredenziali() throws SQLException {

		Amministratore nuovo = AmministratoreDAO.controllaCredenziali(admin.getEmail(), admin.getPassword());
		
		Assert.assertEquals(admin.getEmail(), nuovo.getEmail());
		Assert.assertEquals(admin.getRuolo(), nuovo.getRuolo());
	}

	@Test
	public void TestdoUpdatePassowrd() throws SQLException {
		
		String oldPassword = admin.getPassword();
		String newPassword = "provapassword";
		
		AmministratoreDAO.doUpdatePassword(newPassword, admin);
		admin.setPassword(newPassword);
		
		Amministratore nuovo = AmministratoreDAO.controllaCredenziali(admin.getEmail(), admin.getPassword());
		
		Assert.assertEquals(admin.getEmail(), nuovo.getEmail());
		Assert.assertNotEquals(oldPassword, nuovo.getPassword());
	}

	@Test
	public void TestdoUpdateEmail() throws SQLException {
		
		String newEmail = "newemailtest@mail.it";
		String oldEmail = admin.getEmail();
		
		AmministratoreDAO.doUpdateEmail(newEmail, admin);
		admin.setEmail(newEmail);
		user.setEmail(newEmail);

		Amministratore nuovo = AmministratoreDAO.controllaCredenziali(admin.getEmail(), admin.getPassword());

		Assert.assertNotEquals(oldEmail, nuovo.getEmail());
	}

	@Test
	public void TestDeleteAdmin() throws SQLException {

		AmministratoreDAO.doDeleteAdmin(admin.getEmail());
		
		Amministratore amministratore = AmministratoreDAO.controllaCredenziali(admin.getEmail(), admin.getPassword());
		
		Assert.assertNotEquals(admin.getEmail(), amministratore.getEmail());
	}

	@After
	public void tearDown() throws SQLException {

		//cancello admin
		con = DBConnection.getConnection();
		stmt = con.prepareStatement(DELETE);
		stmt.setString(1, admin.getEmail());
		stmt.executeUpdate();
		stmt.close();
		con.close();
		admin = null;
		
		//cancello utente
		UtenteDAO.doDeleteUser(user.getEmail());
		user = null;
	}

}