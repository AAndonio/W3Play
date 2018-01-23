package controller.utente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CartaDiCredito;
import bean.Amministratore;
import bean.Carrello;
import bean.Utente;

/**
 * Servlet implementation class editUserInfo
 */
@WebServlet("/editProfileInfo")
public class GestioneProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestioneProfiloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logout(user, adm, request);
			// request.setAttribute("risultato", "Account Cancellato");
			direzione = "/homepage";

		} else if (action.equals("removeUtente")) {
			try {
				user.eliminaUtente(user.getEmail());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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

	private void logout(Utente user, Amministratore adm, HttpServletRequest request) {
		
		if (user.getEmail() != null) {
			user.setCognome(null);
			user.setNome(null);
			user.setEmail(null);
			user.setPassword(null);
			user.setCarrello(new Carrello());
			user.setStato("unlogged");
		} else {

			adm.setRuolo(null);
			adm.setUtente(null);
			adm.setStato("unlogged");
		}
		request.getSession().invalidate();

	}

	private void eliminaUtente(Utente utente) {

		try {
			utente.eliminaUtente(utente.getEmail());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void modificaIndirizzo(String via, String numeroCivico, String cap, String citta, Utente user) {
		user.setVia(via);
		user.setCivico(numeroCivico);
		user.setCap(cap);
		user.setCitta(citta);

		try {
			Utente.aggiornaIndirizzo(via, numeroCivico, cap, citta, user.getEmail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modificaPassword(String password, Utente utente) {
		utente.setPassword(password);

		try {

			utente.aggiornaPassword(password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modificaPasswordA(String password, Amministratore adm) {
		adm.setPassword(password);

		try {

			adm.aggiornaPassword(password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
