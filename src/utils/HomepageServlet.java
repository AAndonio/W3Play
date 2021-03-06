package utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Amministratore;
import bean.Prodotto;
import bean.Utente;

/**
 * Homepage del sito, carica i prodotti in ultimi arrivi, e pi� venduti oltre a inizializzare utente e amministratore
 * @author Antonio
 */
@WebServlet("/homepage")
public class HomepageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public HomepageServlet() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Prodotto> piuVenduti = new ArrayList<Prodotto>();
		piuVenduti = null;

		ArrayList<Prodotto> ultimiArrivi = new ArrayList<Prodotto>();
		ultimiArrivi = null;

		try {
			ultimiArrivi = Prodotto.ultimiArrivi();
			piuVenduti = Prodotto.piuVenduti();

		} catch (SQLException e) {
			e.printStackTrace();

			System.err.println(e.getMessage());
		}
		
		HttpSession session = request.getSession();
		
		session.setAttribute("ultimiArrivi", ultimiArrivi);
		session.setAttribute("piuVenduti", piuVenduti);

		request.setAttribute("ultimiArrivi", ultimiArrivi);

		if (session.getAttribute("utente") == null) {
			Utente user = new Utente();
			session.setAttribute("utente", user);
		}
		if (session.getAttribute("admin") == null) {
			Amministratore adm = new Amministratore();
			session.setAttribute("admin", adm);
		}

		session.setMaxInactiveInterval(180 * 60);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		doPost(request, response);
	}
}
