package controller.carrello;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Carrello;
import bean.Prodotto;
import bean.Utente;
import model.ProdottoDAO;
import util.IO;

/**
 * Servlet implementation class cartServlet
 */
@WebServlet("/cartServlet")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarrelloServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utente user = (Utente) request.getSession().getAttribute("utente");
		
		IO.println("CarrelloServlet.doGet");
		
		if (user == null)
			throw new IOException("CarrelloServlet.doGet: utente nullo!");

		if (user.isLoggato()) {
			
			Carrello cart = loadCart(user);
			cart.fetchArticoli();
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/cartPage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		Utente user = (Utente) request.getSession().getAttribute("utente");
		
		IO.println("CarrelloServlet.doPost, action: " + action);
		
		if (user == null)
			throw new IOException("CarrelloServlet.doPost: utente nullo!");
		
		Carrello carrello = user.getCarrello();
		if (user.isLoggato()) {
			
			carrello = loadCart(user);
			carrello.fetchArticoli();
		}
		
		if (action.equals("removeItem")) {
			
			try {
				
				Prodotto prodotto = ProdottoDAO.doRetrieveByKey(Integer.parseInt(request.getParameter("idProdotto")));
				carrello.removeProdotto(prodotto);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (action.equals("addItem")) {
			
			Prodotto product = (Prodotto) request.getSession().getAttribute("prodotto");
			int quantita = Integer.parseInt(request.getParameter("quantita"));
			
			if (quantita < 1 || quantita > 10) {
				
				System.out.println("Quantit√† sbagliata");
				
			} else {
					
				//se il prodotto Ë presente nel carrello...
				try {
					if (carrello.contieneProdotto(product))
						
						carrello.aggiornaQuantit‡Prodotto(product.getIdProdotto(), quantita); //..ne aggiorna la quantit‡
					else
						carrello.addProdotto(product, quantita); //..lo aggiunge
					
				} catch (Exception e) {
					IO.println("CarrelloServlet: errore aggiorna quantit‡ prodotto");
					e.printStackTrace();
				}
			}

		} else if (action.equals("decreaseQuantity")) {
			
			int prodotto    = Integer.parseInt(request.getParameter("idProdotto"));
			int quantitaOld = Integer.parseInt(request.getParameter("quantita"));

			if (quantitaOld > 1) {
				
				try { 
					carrello.aggiornaQuantit‡Prodotto(prodotto, -1);
					
				} catch (SQLException e) {
					IO.println("CarrelloServlet: errore aggiorna quantit‡ (-1) prodotto");
					e.printStackTrace();
				}
			}

		} else if (action.equals("addQuantity")) {

			int prodotto    = Integer.parseInt(request.getParameter("idProdotto"));
			int quantitaOld = Integer.parseInt(request.getParameter("quantita"));

			if (quantitaOld < 10) {
				
				try {
					carrello.aggiornaQuantit‡Prodotto(prodotto, 1);
					
				} catch (SQLException e) {
					IO.println("CarrelloServlet: errore aggiorna quantit‡ (1) prodotto");
					e.printStackTrace();
				}
			}
		}
		
		response.sendRedirect("PostRedirectGetCartServlet");
	}

	/** Carica e aggiunge il carrello all'utente */
	private Carrello loadCart(Utente user) {

		try {

			Carrello cart = Carrello.getCarrelloUtente(user);
			user.setCarrello(cart);
			
			return cart;
			
		} catch (SQLException e) {

			IO.println("CarrelloServlet: errore nel recupero del carrello utente!");
			e.printStackTrace();
		}

		return user.getCarrello();
	}
}
