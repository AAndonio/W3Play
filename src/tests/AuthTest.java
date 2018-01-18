package tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import bean.Utente;

import org.openqa.selenium.*;



public class AuthTest {

	@Test
	public void testAuthentication1() throws SQLException {
		
		assertNull(Utente.controllaCredenziali("antoniovivone96@hotmail.com", null).getEmail()); //TC_1.1_2
	}
	
	@Test
	public void testAuthentication2() throws SQLException {
		assertNull(Utente.controllaCredenziali("antoniovivone96@hotmail.com", null).getEmail()); //TC_1.1_2
	}
	
	@Test
	public void testAuthentication2Prova() throws SQLException {
	
	}
	
	@Test
	public void testAuthentication3() throws SQLException {
		assertNull(Utente.controllaCredenziali("ant96@hotmail.commercial","antonio" ).getEmail()); //TC_1.1_3
	}
	
	@Test
	public void testAuthentication4() throws SQLException {
		assertNull(Utente.controllaCredenziali("ant96@hotmail.commercial","antonio" ).getEmail()); //TC_1.1_4
	}
	
	
	
}
