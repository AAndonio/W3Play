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
import model.OggettoCarrello;
import model.OggettoCarrelloDAO;

/**
 * Servlet implementation class cartServlet
 */
@WebServlet("/cartServlet")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarrelloServlet() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente user = (Utente) request.getSession().getAttribute("utente");
		if(user.getStato().equals("loggato")) {
			loadCart(user);
			loadCartItems(user);
			user.getCarrello().aggiornaPrezzoCarrello();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/cartPage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		Utente user = (Utente) request.getSession().getAttribute("utente");
		System.out.println(action);
		
		if(action.equals("removeItem")) {
			
				int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
				user.getCarrello().removeItem(idProdotto);
				if(user.getStato().equals("loggato")) {
					try {
						OggettoCarrello.removeItemFromCart(idProdotto, user.getCarrello().getIdCarrello());
					} catch (SQLException e) {
			
						e.printStackTrace();
					}
					loadCart(user);
					loadCartItems(user);
				}
			
		}
		else if(action.equals("addItem")) {
			Prodotto product = (Prodotto) request.getSession().getAttribute("prodotto");
			int quantita = Integer.parseInt(request.getParameter("quantita"));
			if(quantita < 1 || quantita >10) {
				System.out.println("Quantità sbagliata");
			}
			else {
			boolean giaPresente = false; //indica se l'articolo Ãš giÃ  presente.
			
			
			
			/* Controllo se l'articolo è già presente nel carrello. In caso positivo se ne aggiorna la quantità */
			for(OggettoCarrello articolo: user.getCarrello().getArticoli()) {
				int quantitaOld = articolo.getQuantita();
				
				if(articolo.getOggetto().getIdProdotto() == product.getIdProdotto()) {
					articolo.setQuantita(quantitaOld + quantita);
					if(user.getStato().equals("loggato")) {
						try {
							OggettoCarrelloDAO.doUpdateItemQuantity(user.getCarrello().getIdCarrello(), product.getIdProdotto(), quantitaOld + quantita);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						loadCart(user);
						loadCartItems(user);
					}
					giaPresente = true;
				}
			}
			
			/* In caso l'articolo non sia presente all'interno del carrello e quindi deve essere aggiunto. */
			if(!giaPresente) {
			OggettoCarrello articolo = new OggettoCarrello(product, quantita);
				
			user.getCarrello().addItem(articolo);
			
			System.out.println(user.getCarrello().getArticoli().size());
				
			if(user.getStato().equals("loggato")) {
				try {
					OggettoCarrello.addItemToCart(articolo, user.getCarrello().getIdCarrello());
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
				loadCart(user);
				loadCartItems(user);
			}
		}
			
		}
		}
		else if(action.equals("decreaseQuantity")) {
			int prodotto = Integer.parseInt(request.getParameter("idProdotto"));
			int quantitaOld = Integer.parseInt(request.getParameter("quantita"));
		
			if(quantitaOld > 1) {
			int nuovaQuantita = user.getCarrello().diminuisciQuantita(prodotto);
			if(user.getStato().equals("loggato")) {
				try {
					OggettoCarrelloDAO.doUpdateItemQuantity(user.getCarrello().getIdCarrello(), prodotto ,nuovaQuantita);
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
				loadCart(user);
				loadCartItems(user);
			}
			
		}
			
		}
		else if(action.equals("addQuantity")) {
			
			int prodotto = Integer.parseInt(request.getParameter("idProdotto"));
			int quantitaOld = Integer.parseInt(request.getParameter("quantita"));
			
			if(quantitaOld < 10) {
			int nuovaQuantita = user.getCarrello().aumentaQuantita(prodotto);
			if(user.getStato().equals("loggato")) {
				try {
					OggettoCarrelloDAO.doUpdateItemQuantity(user.getCarrello().getIdCarrello(), prodotto ,nuovaQuantita);
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
				loadCart(user);
				loadCartItems(user);
			}
		}
			
		}
		user.getCarrello().aggiornaPrezzoCarrello();
		response.sendRedirect("PostRedirectGetCartServlet");
}



	private void loadCart(Utente user){
		try {
			user.setCarrello(Carrello.recuperaCarrelloByUser(user.getEmail()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadCartItems(Utente user){
		Carrello cart = user.getCarrello();
		try {
			cart.setArticoli(OggettoCarrello.recuperaArticoliByCarrello(cart.getIdCarrello()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
