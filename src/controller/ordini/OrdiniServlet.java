
package controller.ordini;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Ordine;
import bean.Utente;
import model.OggettoOrdine;


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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utente user = (Utente) request.getSession().getAttribute("utente");
		ArrayList<Ordine> ordini = new ArrayList<Ordine>();
		try {
			 ordini = Ordine.searchOrdine(user.getEmail());
			 ordini = OggettoOrdine.recuperaOggetti(ordini);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.setOrdini(ordini);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/customerPage.jsp");
		dispatcher.forward(request, response);
		}
		
		
}

