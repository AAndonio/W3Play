
package controller.ordini;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Ordine;
import bean.Utente;
import util.IO;

/**
 * Servlet implementation class orderServlet
 */
@WebServlet("/orderServlet")
public class OrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrdiniServlet() {
		super();
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

		IO.println("OrdiniServlet.doPost");

		Utente user = (Utente) request.getSession().getAttribute("utente");
		List<Ordine> ordini = new ArrayList<Ordine>();
		
		try {
			ordini = Ordine.searchOrdine(user);
			
		} catch (SQLException e) {
			IO.println("OrdiniServlet.doPost: errore recupero ordini utente!");
			e.printStackTrace();
		}

		user.setOrdini(ordini);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/customerPage.jsp");
		dispatcher.forward(request, response);
	}

}
