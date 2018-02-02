package controller.ordini;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Carrello;
import bean.Ordine;
import bean.Prodotto;
import bean.Utente;

import util.Entry;
import util.IO;

/**
 * Servlet per la gestione del pagamento dell'ordine
 * @author Augusto
 */
@WebServlet("/checkoutServlet")
public class CheckoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public CheckoutServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utente user = (Utente) request.getSession().getAttribute("utente");
		String direzione = null;
		if (user.getStato().equals("loggato")) {
			direzione = "/WEB-INF/checkoutPage.jsp";
		} else {
			direzione = "/WEB-INF/loginPage.jsp";
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Utente user = (Utente) request.getSession().getAttribute("utente");
		String action = request.getParameter("action");

		IO.println("CheckoutServlet.doPost: " + action);
		
		if (action.equals("riepilogo")) {
			
			Carrello carrello = user.getCarrello();
			String carta = request.getParameter("payment");
			
			if (carta == null)
				throw new IOException("CheckoutServlet.doPost: carta di credito nulla!");
			
			ArrayList<Prodotto> articoli = new ArrayList<Prodotto>();
			Ordine ordine = null;
			
			if (carta.equals("alternative")) {
				
				ordine = new Ordine(carrello.getPrezzoTotale(), LocalDate.now(),
						request.getParameter("credit-card-alternative"), user.getEmail(), articoli);
			} else {
				ordine = new Ordine(carrello.getPrezzoTotale(), LocalDate.now(), carta, user.getEmail(),
						articoli);
			}

			user.getOrdini().add(ordine);
				
			//aggiungo gli oggetti nel carrello in ordine
			List<Entry<Prodotto, Integer>> prodotti = carrello.getListaArticoli();
			
			prodotti.forEach((e) -> {
				
				Prodotto prodotto = e.getKey();
				articoli.add(prodotto);
				
				try {
					prodotto.vendi();
					
				} catch (SQLException e1) {
					
					IO.println("CheckoutServlet.doPost: errore vendita articolo: " + prodotto.getIdProdotto() + " \n\n" + e1.getMessage());
					e1.printStackTrace();
				}
			});

			ordine.setArticoli(articoli);

			try {
				ordine.setIdOrdine(Ordine.writeOrder(ordine));

			} catch (SQLException e) {
				IO.println("CheckoutServlet.doPost: errore con writeOrder:\n\n" + e.getMessage());
				e.printStackTrace();
			}
			
			//articoli venduti, ora si svuota il carrello
			user.getCarrello().svuota();

			response.sendRedirect("PostRedirectGetCheckoutServlet");
		}
	}

}