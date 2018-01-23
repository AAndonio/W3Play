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
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if (adm.getEmail() != null) {
				
				adm.setStato("loggato");
				
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
					user.setStato("loggato");
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
				user.setStato("unlogged");
				
			} else {

				// TODO: dare una controllata qui!!!
				Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
				adm.setRuolo(null);
				adm.setUtente(null);
				adm.setStato("unlogged");
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
				adm.setStato("unlogged");

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
				user.setStato("loggato");
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

		if (adm != null && adm.getEmail() != null) {
			direzione = "/WEB-INF/adminPage.jsp";
		} else if (user != null && user.getStato().equals("unlogged") && adm.getStato().equals("unlogged")) {
			direzione = "/WEB-INF/loginPage.jsp";
		} else {
			direzione = "/WEB-INF/customerPage.jsp";
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