package controller.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Utente;
import model.UtenteDAO;
import util.IO;
import utils.Email;

/**
 * Servlet implementation class RecuperoPassword
 */
@WebServlet("/RecuperoPassword")
public class RecuperoPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperoPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IO.println("Server: recupero password servlet");

		String email = request.getParameter("email");
		
		if (email != null) {
			
			Utente utente = new Utente();
			
			try {
				
				utente = utente.getPass(email);
				
				if (utente != null) {
					
					//invia l'email
					Email.send(email, utente.getPassword());
					
					//segnala l'invio con successo
					response.setHeader("alert", "positive");
					
					IO.println("RecuperoPasswordServlet: email inviata con successo!");
					
				} else {
					//mostra una alert di errore
					response.setHeader("alert", "Email inserita non valida, riprovare.");
				
					IO.println("RecuperoPasswordServlet: email specificata non valida!");
				}
				
			} catch (SQLException e) {
				
				IO.println("RecuperoPasswordServlet: errore nel recupero dell'utente a partire dalla email");
				e.printStackTrace();
				
				//mostra una alert di errore
				response.setHeader("alert", "Email inserita non valida, riprovare.");
			}
		}
		
		//response.sendRedirect("/W3Play/login");
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/loginPage.jsp");
		dispatcher.include(request, response);
		
	}
}
