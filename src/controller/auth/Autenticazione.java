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
import javax.servlet.http.HttpSession;

import bean.Carrello;
import bean.CartaDiCredito;
import bean.Amministratore;
import bean.Utente;

/**
 * Servlet per la gestione dell'Autenticazione
 * 
 * @author Alfonso
 */
@WebServlet("/login")
public class Autenticazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Autenticazione() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		Utente user = (Utente) session.getAttribute("utente");

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
				
				session.setAttribute("admin", adm);
				
				direzione = "/WEB-INF/adminPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);

			} else {

				Utente tmp = null;

				try {
					tmp = Utente.controllaCredenziali(username, password);

				} catch (SQLException e) {
					IO.println("Autenticazione: errore utente.controllaCredenziali [login, registration]");
					e.printStackTrace();
				}

				if (tmp != null && tmp.getEmail() != null) {

					user = tmp;
					user.setOrdini(new ArrayList<>());
					user.setStato(Utente.LOGGATO);

					loadCreditCard(user);
					mergeCarrelli(user);

					session.setAttribute("utente", user);

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

				user = new Utente();
				user.setStato(Utente.UNLOGGED);

			} else {

				// TODO: dare una controllata qui!!!
				Amministratore adm = (Amministratore) session.getAttribute("admin");
				adm.setRuolo(null);
				adm.setUtente(null);
				adm.setStato(Utente.UNLOGGED);
			}

			session.invalidate();

			direzione = "/homepage";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
			dispatcher.forward(request, response);

		} else if (action.equals("admintouser")) {

			IO.println("Autenticazione: cambia ruolo!");

			Amministratore adm = (Amministratore) session.getAttribute("admin");

			user = adm.getUtente();

			adm.setRuolo(null);
			adm.setUtente(null);
			adm.setStato(Utente.UNLOGGED);

			if (user == null || user.getEmail() == null) {
				IO.println("Autenticazione: admin_to_user -> l'utente è nullo!!");

				user.setStato("errore");
				session.setAttribute("utente", user);

				direzione = "/WEB-INF/loginPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);

			} else {

				user.setStato(Utente.LOGGATO);
				loadCreditCard(user);
				session.setAttribute("utente", user);

				// notifica cambia ruolo (serve per poi tornare ad essere admin)
				session.setAttribute("cambia_ruolo", "true");

				//
				mergeCarrelli(user);

				direzione = "/orderServlet";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
			}

		} else if ("user_to_admin".equals(action)) {

			if (user != null && user.getEmail() != null) {
				
				user.setStato(Utente.UNLOGGED);
				session.setAttribute("utente", user);
				
				try {
					Amministratore admin = Amministratore.controllaCredenziali(user.getEmail(), user.getPassword());
					admin.setStato(Utente.LOGGATO);
					admin.setUtente(user);
					
					session.setAttribute("admin", admin);
					session.setAttribute("cambia_ruolo", "false");
					
					direzione = "/WEB-INF/adminPage.jsp";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
					dispatcher.forward(request, response);
					
				} catch (SQLException e) {
					IO.println("Autenticazione: [USER_TO_ADMIN] errore Amministratore.controllaCredenziali!");
					e.printStackTrace();
				}
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
		boolean adminCondition = adm.isLoggato() && user.isNotLoggato();
		boolean userCondition = user.isLoggato() && adm.isNotLoggato();

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
			Carrello carrelloDatabase = Carrello.getCarrelloUtente(user);
			carrelloDatabase.fetchArticoli();

			IO.printf("carrelloVisitatore: %d, carrelloDB: %d\n", carrelloVisitatore.getArticoli().size(),
					carrelloDatabase.getArticoli().size());

			// merge
			user.setCarrello(carrelloDatabase); // settare il carrello all'utente prima del merge, altrimenti non salva le relazioni sul DB
			carrelloDatabase.merge(carrelloVisitatore);

		} catch (SQLException e) {
			IO.println("Autenticazione: merge dei carrelli fallito!");
		}
	}
}