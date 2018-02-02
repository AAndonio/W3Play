package controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Statistiche;

/**
 * Servlet per il calcolo delle statistiche di sistema.
 * @author Luca
 */
@WebServlet("/StatisticheServlet")
public class StatisticheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StatisticheServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Statistiche stats = new Statistiche();
		stats.aggiorna();
		
		request.getSession().setAttribute("statistiche", stats);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/statsPage.jsp");
		dispatcher.forward(request, response);
	}
}
