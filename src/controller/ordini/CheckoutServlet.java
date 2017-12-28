package controller.ordini;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Ordine;
import bean.Utente;
import model.OggettoCarrello;
import model.OggettoOrdine;

/**
 * Servlet implementation class checkoutServlet
 */
@WebServlet("/checkoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente user = (Utente) request.getSession().getAttribute("utente");
		String direzione = null;
		if(user.getStato().equals("loggato")) {
			direzione = "/WEB-INF/checkoutPage.jsp";
		}
		else {
			direzione = "/WEB-INF/loginPage.jsp";
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente user = (Utente) request.getSession().getAttribute("utente");
		
		String action = request.getParameter("action");
		if(action.equals("riepilogo")) {
			String carta = request.getParameter("payment");
			ArrayList<OggettoOrdine> articoli = new ArrayList<OggettoOrdine>();
			Ordine ordine = null;
			if(carta.equals("alternative")) {
				ordine = new Ordine(user.getCarrello().getPrezzoTotale(), LocalDate.now(),request.getParameter("credit-card-alternative"), user.getEmail(),articoli);
			}else {
				ordine = new Ordine(user.getCarrello().getPrezzoTotale(), LocalDate.now(),carta, user.getEmail(),articoli);
			}
			
			user.getOrdini().add(ordine);
			
			for(OggettoCarrello articolo: user.getCarrello().getArticoli()) {
				articoli.add(new OggettoOrdine(articolo.getOggetto(), articolo.getQuantita()));
				try {
					articolo.getOggetto().vendi();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			ordine.setArticoli(articoli);
			
			try {
				ordine.setIdOrdine(Ordine.writeOrder(ordine));
				OggettoOrdine.addItemByOrder(ordine);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.getCarrello().setArticoli(new ArrayList<OggettoCarrello>());
			try {
				OggettoCarrello.removeItemFromCart(user.getCarrello().getIdCarrello());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect("PostRedirectGetCheckoutServlet");
		}
	}

}