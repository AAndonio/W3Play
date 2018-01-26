package controller.utente;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Amministratore;
import bean.Carrello;
import bean.Utente;

/**
 * Servlet per la gestione del profilo di utente e amministratore
 * @author Augusto
 */
@WebServlet("/editProfileInfo")
public class GestioneProfiloServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public GestioneProfiloServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println(action);
		String direzione = "/homepage";

		Utente user = (Utente) request.getSession().getAttribute("utente");

		if (action.equals("removeUtenteA")) {
			Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
			try {

				adm.eliminaAdmin(adm.getEmail());
				user.eliminaUtente(adm.getEmail());
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
			logout(user, adm, request);
			// request.setAttribute("risultato", "Account Cancellato");
			direzione = "/homepage";

		} else if (action.equals("removeUtente")) {
			
			try {
				user.eliminaUtente(user.getEmail());
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			
			logout(user, null, request);
			direzione = "/homepage";
		}

		else if (action.equals("modificaIndirizzo")) {
			
			String via = request.getParameter("via");
			String numeroCivico = request.getParameter("numeroCivico");
			String cap = request.getParameter("cap");
			String citta = request.getParameter("citta");

			if (via.matches("[A-za-z]*$") && numeroCivico.matches("[0-9]*$") && cap.matches("[0-9]*$")
					&& citta.matches("[a-zA-Z ]*$")) {
				
				modificaIndirizzo(via, numeroCivico, cap, citta, user);
				direzione = "/WEB-INF/customerPage.jsp";
			}
			
		} else if (action.equals("modificaPassword")) {

			String newPass = request.getParameter("new-password1");
			
			if (newPass.matches("[a-zA-Z0-9 ]*$")) {
				modificaPassword(newPass, user);
				direzione = "/WEB-INF/customerPage.jsp";
			}
			
		} else if (action.equals("modificaPasswordA")) {
			
			Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");

			String newPass1 = request.getParameter("new-password1");
			modificaPasswordA(newPass1, adm);
			direzione = "/WEB-INF/adminPage.jsp";
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
		dispatcher.forward(request, response);
	}
	
	/**
	 * Logout di utente/amministratore
	 */
	private void logout(Utente user, Amministratore adm, HttpServletRequest request) {
		
		if (user.getEmail() != null) {
			user.setCognome(null);
			user.setNome(null);
			user.setEmail(null);
			user.setPassword(null);
			user.setCarrello(new Carrello());
			user.setStato(Utente.UNLOGGED);
			
		} else {
			adm.setRuolo(null);
			adm.setUtente(null);
			adm.setStato(Utente.UNLOGGED);
		}
		
		request.getSession().invalidate();
	}

	/**
	 * Modifica indirizzo utente (via, civico, cap, città)
	 */
	public void modificaIndirizzo(String via, String numeroCivico, String cap, String citta, Utente user) {
		
		user.setVia(via);
		user.setCivico(numeroCivico);
		user.setCap(cap);
		user.setCitta(citta);

		try {
			Utente.aggiornaIndirizzo(via, numeroCivico, cap, citta, user.getEmail());
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifica password utente
	 * @param password: nuova password
	 */
	private void modificaPassword(String password, Utente utente) {
		
		utente.setPassword(password);

		try {
			utente.aggiornaPassword(password);

		} catch (SQLException e) {
			// 
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifica password amministratore
	 * @param password: nuova password
	 */
	private void modificaPasswordA(String password, Amministratore adm) {
		
		adm.setPassword(password);

		try {
			adm.aggiornaPassword(password);

		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
}
