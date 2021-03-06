package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Utente;

/**
 * Servlet per il controllo della email
 * 
 * @author Alfonso
 */
@WebServlet("/EmailControlServlet")
public class EmailControlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public EmailControlServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		String email = request.getParameter("email");
		
		boolean isUsed = false;
		try {
			isUsed = Utente.checkEmail(email);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		System.out.println(isUsed);
		
		if (isUsed) {
			out.write("true");
		} else {
			out.write("false");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}