package controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Prodotto;
import model.ProdottoDAO;

/**
 * Servlet per l'aggiunta/rimozione dei prodotti nel catalogo, 
 * e ricerca dei prodotto nell varie categorie (ultimi arrivi, piu venduti..).
 * @author Augusto
 */
@WebServlet("/productServlet")
@MultipartConfig
public class ProdottoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ProdottoServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int idProdotto = Integer.parseInt(request.getParameter("id"));
		String action = request.getParameter("action");

		if (action.equals("directUltimiArrivi")) {
			Prodotto product = cercaProdotto(idProdotto,
					(ArrayList<Prodotto>) request.getSession().getAttribute("ultimiArrivi"));
			
			request.getSession().setAttribute("prodotto", product);
			
		} else if (action.equals("directPiuVenduti")) {
			Prodotto product = cercaProdotto(idProdotto,
					(ArrayList<Prodotto>) request.getSession().getAttribute("piuVenduti"));
			
			request.getSession().setAttribute("prodotto", product);
		}

		else {
			ArrayList<Prodotto> risultati = (ArrayList<Prodotto>) request.getSession().getAttribute("risultati");

			Prodotto product = cercaProdotto(idProdotto, risultati);

			request.getSession().setAttribute("prodotto", product);
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/productPage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("addObject")) {

			String nome = request.getParameter("product-name");
			String produttore = request.getParameter("product-producer");
			String piattaforma = request.getParameter("product-platform");
			String genere = request.getParameter("product-genere");
			String descrizione = request.getParameter("product-description");
			String prezzo = request.getParameter("product-price");
			String disponibilita = request.getParameter("disponibili");
			String video = request.getParameter("product-linkVideo");

			if (nome.matches("[0-9a-zA-Z/:///-/' ]*$") && produttore.matches("[0-9a-zA-Z /:///-/']*$")
					&& piattaforma.matches("[0-9a-zA-Z/:///-/' ]*$") && genere.matches("[A-Za-z /']*$")
					&& descrizione.matches("[0-9a-zA-Z /:///-/']*$") && prezzo.matches("[0-9/.]*$")
					&& disponibilita.matches("[0-9]*$")) {

				Prodotto articolo = new Prodotto();
				LocalDate date = LocalDate.parse(request.getParameter("product-release-date"));

				articolo.setNome(request.getParameter("product-name"));
				articolo.setProduttore(request.getParameter("product-producer"));
				articolo.setPiattaforma(request.getParameter("product-platform"));
				articolo.setGenere(request.getParameter("product-genere"));
				articolo.setDescrizione(request.getParameter("product-description"));

				RequestDispatcher dispatcher1 = getServletContext().getRequestDispatcher("/ImageUpload");
				dispatcher1.include(request, response);

				articolo.setImmagine((String) request.getAttribute("imagePath"));
				articolo.setPrezzo(Float.parseFloat(request.getParameter("product-price")));
				articolo.setDisponibilita(Integer.parseInt(request.getParameter("disponibili")));
				articolo.setDataUscita(date);
				articolo.setLinkVideo(request.getParameter("product-linkVideo"));

				try {
					Prodotto.addProduct(articolo);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("risultato", "Prodotto aggiunto");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/adminPage.jsp");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("risultato", "Prodotto non aggiunto");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/adminPage.jsp");
				dispatcher.forward(request, response);
			}
		} else if (action.equals("removeProdotto")) {

			int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

			try {
				ProdottoDAO.removeProduct(idProdotto);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("risultato", "Prodotto rimosso con successo");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/index.jsp");
			dispatcher.forward(request, response);

		} else {

			String nome = request.getParameter("product-name");
			String produttore = request.getParameter("product-producer");
			String piattaforma = request.getParameter("product-platform");
			String genere = request.getParameter("product-genere");
			String descrizione = request.getParameter("product-description");
			String prezzo = request.getParameter("product-price");
			String disponibilita = request.getParameter("disponibili");
			String video = request.getParameter("product-linkVideo");

			if (nome.matches("[0-9a-zA-Z ]*$") && produttore.matches("[0-9a-zA-Z ]*$")
					&& piattaforma.matches("[0-9a-zA-Z ]*$") && genere.matches("[A-Za-z ]*$")
					&& descrizione.matches("[0-9a-zA-Z ]*$") && prezzo.matches("[0-9/.]*$")
					&& disponibilita.matches("[0-9]*$")) {

				Prodotto articolo = new Prodotto();
				LocalDate date = LocalDate.parse(request.getParameter("product-release-date"));

				articolo.setIdProdotto(Integer.parseInt(request.getParameter("idProdotto")));
				articolo.setNome(request.getParameter("product-name"));
				articolo.setProduttore(request.getParameter("product-producer"));
				articolo.setPiattaforma(request.getParameter("product-platform"));
				articolo.setGenere(request.getParameter("product-genere"));
				articolo.setDescrizione(request.getParameter("product-description"));

				RequestDispatcher dispatcher1 = getServletContext().getRequestDispatcher("/ImageUpload");
				dispatcher1.include(request, response);

				articolo.setImmagine((String) request.getAttribute("imagePath"));
				articolo.setPrezzo(Float.parseFloat(request.getParameter("product-price")));
				articolo.setDisponibilita(Integer.parseInt(request.getParameter("disponibili")));
				articolo.setDataUscita(date);
				articolo.setLinkVideo(request.getParameter("product-linkVideo"));

				try {
					Prodotto.updateProduct(articolo);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.getSession().setAttribute("prodotto", articolo);
				request.setAttribute("risultato", "Prodotto modificato");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/productPage.jsp");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("risultato", "Prodotto non modificato");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/productPage.jsp");
				dispatcher.forward(request, response);
			}
		}

	}

	/* (non-Javadoc) */
	private static Prodotto cercaProdotto(int id, ArrayList<Prodotto> risultati) {
		Prodotto prod = new Prodotto();
		for (Prodotto p : risultati) {
			if (p.getIdProdotto() == id)
				prod = p;
		}
		return prod;
	}
}
