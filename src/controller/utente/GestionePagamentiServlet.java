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
import bean.Utente;

/**
 * Servlet per la gestione delle Carte di credito dell'utente
 * @author Antonio
 */
@WebServlet("/editCartInfo")
public class GestionePagamentiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public GestionePagamentiServlet() {
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
		Utente user = (Utente) request.getSession().getAttribute("utente");
		
		String direzione = null;

		if (action.equals("aggiungiCarta")) {
			
			String numeroCarta = request.getParameter("numeroCarta");
			String titolare = request.getParameter("Titolare");
			LocalDate date = LocalDate.parse(request.getParameter("Scadenza"));
			int ccv = Integer.parseInt(request.getParameter("ccv"));
			
			CartaDiCredito carta = new CartaDiCredito(numeroCarta, titolare, date, ccv);
			user.getCarte().add(carta);

			try {
				
				CartaDiCredito.addCreditCard(carta);
				CartaDiCredito.associaCartaUtente(carta.getNumerocarta(), user.getEmail());
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			direzione = "/WEB-INF/customerPage.jsp";
			
		} else if (action.equals("aggiornaCarta")) {
			
			String numeroCartaVecchio = request.getParameter("cartaDaAggiornare");
			String numeroCartaNuovo = request.getParameter("numeroCarta");
			String titolare = request.getParameter("Titolare");
			LocalDate date = LocalDate.parse(request.getParameter("Scadenza"));
			int ccv = Integer.parseInt(request.getParameter("ccv"));
			
			CartaDiCredito carta = new CartaDiCredito(numeroCartaNuovo, titolare, date, ccv);

			aggiornaCartaUtente(numeroCartaVecchio, carta, user);
			
			direzione = "/WEB-INF/customerPage.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
		dispatcher.forward(request, response);
	}

	/* (non-Javadoc) */
	private void aggiornaCartaUtente(String numeroVecchio, CartaDiCredito nuova, Utente utente) {

		try {
			
			CartaDiCredito.eliminaAssociazione(numeroVecchio, utente.getEmail());
			CartaDiCredito.updateCarta(numeroVecchio, nuova);
			CartaDiCredito.associaCartaUtente(nuova.getNumerocarta(), utente.getEmail());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		loadCreditCard(utente);
	}

	/* (non-Javadoc) */
	private void loadCreditCard(Utente user) {
		
		try {
			
			user.setCarte(CartaDiCredito.recuperaCarteByUtente(user.getEmail()));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
