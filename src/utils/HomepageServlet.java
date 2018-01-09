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

import bean.Amministratore;
import bean.Prodotto;
import bean.Utente;


	@WebServlet("/homepage")
	public class HomepageServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
	   
	    public HomepageServlet() {
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			request.getSession().setAttribute("ultimiArrivi", ultimiArrivi);
			request.getSession().setAttribute("piuVenduti", piuVenduti);
			
              
               request.setAttribute("ultimiArrivi", ultimiArrivi);
              
              
               if(request.getSession().getAttribute("utente") == null) {
                   Utente user = new Utente();
                   request.getSession().setAttribute("utente",user);
               }
               if(request.getSession().getAttribute("admin") == null) {
                   Amministratore adm = new Amministratore();
                   request.getSession().setAttribute("admin",adm);
               }
              
              
              request.getSession().setMaxInactiveInterval(180 * 60);
               RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
               dispatcher.forward(request, response);
           }


		

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doPost(request, response);
		}
}
