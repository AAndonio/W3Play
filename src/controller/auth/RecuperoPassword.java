package controller.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Utente;
import util.IO;
import utils.Email;

/**
 * Servlet per il recupero della password
 * @author Luca
 */
@WebServlet("/RecuperoPassword")
public class RecuperoPassword extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// pattern per la validazione dell'email
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[-a-z0-9_}{\\'?]+(\\.[-a-z0-9_}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Valida un indirizzo email.
	 * 
	 * @param emailStr:
	 *            email da validare
	 * @return restituisce un booleano: true nel caso in cui l'email rispetta il
	 *         formato e false altrimenti.
	 */
	public static boolean validate(String emailStr) {

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);

		return matcher.find();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		IO.println("Server: recupero password servlet");

		String email = request.getParameter("email");

		if (email != null) {

			if (!validate(email)) {

				// mostra una alert di errore
				response.setHeader("alert", "L'email inserita non rispetta il formato, riprovare.");

				IO.println("RecuperoPasswordServlet: email non rispetta il formato!");

			} else {

				Utente utente = new Utente();

				try {

					utente = utente.getPass(email);

					if (utente != null) {

						// invia l'email
						Email.send(email, utente.getPassword());

						// segnala l'invio con successo
						response.setHeader("alert", "positive");

						IO.println("RecuperoPasswordServlet: email inviata con successo!");

					} else {
						// mostra una alert di errore
						response.setHeader("alert", "L'email inserita non corrisponde ad un account, riprovare.");

						IO.println("RecuperoPasswordServlet: email non corrisponde ad un account!");
					}

				} catch (SQLException e) {

					IO.println("RecuperoPasswordServlet: errore nel recupero dell'utente a partire dalla email");
					e.printStackTrace();

					// mostra una alert di errore
					response.setHeader("alert", "Errore del server, riprovare.");
				}
			}
		}

		// response.sendRedirect("/W3Play/login");

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/loginPage.jsp");
		dispatcher.include(request, response);

	}
}
