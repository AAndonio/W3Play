package controller.auth;

import model.UtenteDAO;
import util.IO;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Carrello;
import bean.Utente;

/**
 * Servlet per la registrazione di un account utente
 * @author Antonio
 */
@WebServlet("/regServlet")
public class Registrazione extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public Registrazione() {}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		String nome = request.getParameter("user-name");
		String cognome = request.getParameter("user-surname");
		String email = request.getParameter("user-email");
		String password = request.getParameter("password");
		String via = request.getParameter("user-via");
		String civico = request.getParameter("user-civico");
		String cap = request.getParameter("user-cap");
		String citta = request.getParameter("user-citta");
		
		if(nome.matches("[A-Za-z ]*$") && cognome.matches("[A-Za-z ]*$")   
				&& password.matches("[0-9a-zA-Z]*$") && via.matches("[0-9a-zA-Z ]*$") 
				&& civico.matches("[0-9]*$") && cap.matches("[0-9]*$") 
				&& citta.matches("[A-Za-z ]*$"))  {
		
		Utente utente = new Utente();
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		utente.setPassword(password);
		utente.setVia(via);
		utente.setCivico(civico);
		utente.setCap(cap);
		utente.setCitta(citta);
		
		try {
			
			UtenteDAO.doSave(utente);
			
			Carrello carrello = Carrello.getCarrelloUtente(utente); //crea il carrello per il nuovo utente
			utente.setCarrello(carrello);
			
		} catch (SQLException e) {
			IO.println("Registrazione: errore salvataggio utente o creazione del carrello!");
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login");
		dispatcher.forward(request, response);
		
	} else {
		System.out.println("Qualcosa di sbagliato!");
	}
}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/registration.jsp");
		dispatcher.forward(request, response);
	}

}
