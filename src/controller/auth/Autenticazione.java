package controller.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import util.IO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Carrello;
import bean.CartaDiCredito;
import bean.Amministratore;
import bean.Ordine;
import bean.Utente;

/**
 * Servlet per la gestione dell'Autenticazione
 * @author Alfonso
 */
@WebServlet("/login")
public class Autenticazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Autenticazione() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		Utente user = (Utente) request.getSession().getAttribute("utente");

		IO.println("Autenticazione.doPost, action: " + action);
		
		response.setContentType("text/html");

		String direzione = null;

		if (action.equals("login") || action.equals("registration")) {
			String username = request.getParameter("user-email");
			String password = request.getParameter("password");

			Amministratore adm = null;
			try {
				adm = Amministratore.controllaCredenziali(username, password);
				
			} catch (SQLException e2) {
				IO.println("Autenticazione.doPost: errore Amministratore.controllaCredenziali");
				e2.printStackTrace();
			}
			
			if (adm != null && adm.getEmail() != null) {
				
				adm.setStato(Utente.LOGGATO);
				
				request.getSession().setAttribute("admin", adm);
				direzione = "/WEB-INF/adminPage.jsp";
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
				
			} else {
				Utente temp = new Utente();

				try {
					temp = Utente.controllaCredenziali(username, password);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (temp.getEmail() != null) {
					
					user.setCognome(temp.getCognome());
					user.setNome(temp.getNome());
					user.setEmail(temp.getEmail());
					user.setPassword(temp.getPassword());
					user.setCap(temp.getCap());
					user.setCitta(temp.getCitta());
					user.setVia(temp.getVia());
					user.setCivico(temp.getCivico());
					user.setOrdini(new ArrayList<Ordine>());
					user.setStato(Utente.LOGGATO);
					loadCreditCard(user);
					
					//
					mergeCarrelli(user);
						
					direzione = "/orderServlet";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
					dispatcher.forward(request, response);
					
				} else {
					user.setStato("errore");
					
					direzione = "/WEB-INF/loginPage.jsp";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
					dispatcher.forward(request, response);
				}
			}
			
		} else if (action.equals("logout")) {
			
			if (user.getEmail() != null) {
				System.out.println("logout");
				
				user.setCognome(null);
				user.setNome(null);
				user.setEmail(null);
				user.setPassword(null);
				user.setCarrello(new Carrello());
				user.setStato(Utente.UNLOGGED);
				
			} else {

				// TODO: dare una controllata qui!!!
				Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
				adm.setRuolo(null);
				adm.setUtente(null);
				adm.setStato(Utente.UNLOGGED);
			}
			
			request.getSession().invalidate();
			direzione = "/homepage";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
			dispatcher.forward(request, response);
			
		} else if (action.equals("admintouser")) {
			
			Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
			Utente temp = new Utente();
			
			try {
				temp = Utente.controllaCredenziali(adm.getEmail(), adm.getPassword());

				adm.setRuolo(null);
				adm.setUtente(null);
				adm.setStato(Utente.UNLOGGED);

			} catch (SQLException e) {
				
				e.printStackTrace();
			}

			if (temp.getEmail() != null) {
				
				user.setCognome(temp.getCognome());
				user.setNome(temp.getNome());
				user.setEmail(temp.getEmail());
				user.setPassword(temp.getPassword());
				user.setCap(temp.getCap());
				user.setCitta(temp.getCitta());
				user.setVia(temp.getVia());
				user.setCivico(temp.getCivico());
				user.setOrdini(new ArrayList<Ordine>());
				user.setStato(Utente.LOGGATO);
				loadCreditCard(user);
				
				mergeCarrelli(user);
				
				direzione = "/orderServlet";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
				
			} else {
				user.setStato("errore");
				direzione = "/WEB-INF/loginPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
			}
		}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		IO.println("Autenticazione.doGet");
		
		String direzione = null;
		Utente user = (Utente) request.getSession().getAttribute("utente");

		Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
		
		IO.println(adm);
		IO.println(user);
		
		if (user == null)
			throw new IOException("Autenticazione.doGet: utente nullo!");
		
		if (adm == null)
			throw new IOException("Autenticazione.doGet: amministratore nullo!");
		
		boolean loginCondition = user.isNotLoggato() && adm.isNotLoggato();
		boolean adminCondition = adm.isLoggato() && user.isNotLoggato();//(adm.getEmail() != null) && (adm.getUtente() != null);
		boolean userCondition  = user.isLoggato() && adm.isNotLoggato();//Utente.UNLOGGED.equals(user.getStato()) && Utente.UNLOGGED.equals(adm.getStato());
		
		if (adminCondition) {
			direzione = "/WEB-INF/adminPage.jsp";
			IO.println("adminCondition");
			
		} else if (userCondition) {
			direzione = "/WEB-INF/customerPage.jsp";
			IO.println("userCondition");
			
		} else if (loginCondition) {
			direzione = "/WEB-INF/loginPage.jsp";
			IO.println("loginCondition");
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
		dispatcher.forward(request, response);
	}

	private void loadCreditCard(Utente user) {
		try {
			user.setCarte(CartaDiCredito.recuperaCarteByUtente(user.getEmail()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Effettua il merge dei carrelli (da visitatore e da utente registrato)
	 */
	private void mergeCarrelli(Utente user) {
		
		try {
			// carrelli visitatore e database
			Carrello carrelloVisitatore = user.getCarrello();
			Carrello carrelloDatabase   = Carrello.getCarrelloUtente(user);
			carrelloDatabase.fetchArticoli();

			IO.printf("carrelloVisitatore: %d, carrelloDB: %d\n", carrelloVisitatore.getArticoli().size(), carrelloDatabase.getArticoli().size());
			
			// merge
			user.setCarrello(carrelloDatabase); //settare il carrello all'utente prima del merge, altrimenti non salva le relazioni sul DB
			carrelloDatabase.merge(carrelloVisitatore);
			
			
		} catch (SQLException e) {
			IO.println("Autenticazione: merge dei carrelli fallito!");
		}
	}
}