package controller.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.OggettoCarrello;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Carrello;
import bean.CartaDiCredito;
import bean.Amministratore;
import bean.Ordine;
import bean.Utente;

@WebServlet("/login")
public class Autenticazione extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Autenticazione() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		Utente user = (Utente) request.getSession().getAttribute("utente");
		response.setContentType("text/html");
		String direzione = null;

		if (action.equals("login") || action.equals("registration")) {
			String username = request.getParameter("user-email");
			String password = request.getParameter("password");

			Amministratore adm = null;
			try {
				adm = Amministratore.controllaCredenziali(username, password);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if (adm.getEmail() != null) {
				adm.setStato("loggato");
				request.getSession().setAttribute("admin", adm);
				direzione = "/WEB-INF/adminPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
			} else {
				Utente temp = new Utente();

				try {
					temp = Utente.controllaCredenziali(username, password);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (temp.getEmail() != null) {
					user.setCognome(temp.getCognome());
					user.setNome(temp.getNome());
					user.setEmail(temp.getEmail());
					user.setPassword(temp.getPassword());
					user.setCap(temp.getCap());
					user.setCitta(temp.getCitta());
					user.setVia(temp.getVia());
					user.setCivico(temp.getCivico());
					user.setOrdini(new ArrayList<Ordine>());
					user.setStato("loggato");
					loadCreditCard(user);
					Carrello visitatore = user.getCarrello();
					loadCart(user);

					if (!visitatore.getArticoli().isEmpty()) {
						ArrayList<OggettoCarrello> articoliDaVisitatore = visitatore.getArticoli();
						ArrayList<OggettoCarrello> articoliDaDatabase = new ArrayList<OggettoCarrello>();
						try {
							articoliDaDatabase = OggettoCarrello.recuperaArticoliByCarrello(
									Carrello.recuperaCarrelloByUser(user.getEmail()).getIdCarrello());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						for (OggettoCarrello o : articoliDaDatabase) {
							System.out.println(o.getOggetto().getNome());
						}

						ArrayList<OggettoCarrello> articoliMix = new ArrayList<OggettoCarrello>();

						OggettoCarrello oggettoTemp = new OggettoCarrello();

						boolean giaAggiunto = false;

						for (OggettoCarrello articoloV : articoliDaVisitatore) {

							for (OggettoCarrello articoloD : articoliDaDatabase) {

								if (articoloV.getOggetto().getIdProdotto() == articoloD.getOggetto().getIdProdotto()) {

									oggettoTemp.setOggetto(articoloV.getOggetto());
									oggettoTemp.setQuantita(articoloV.getQuantita() + articoloD.getQuantita());
									System.out.println(oggettoTemp.getQuantita());

									try {
										OggettoCarrello.updateItemQuantity(
												Carrello.recuperaCarrelloByUser(user.getEmail()).getIdCarrello(),
												oggettoTemp.getOggetto().getIdProdotto(), oggettoTemp.getQuantita());
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									articoliMix.add(oggettoTemp);
									giaAggiunto = true;
								} else {
									articoliMix.add(articoloD);
								}
							}
							if (!giaAggiunto) {
								articoliMix.add(articoloV);
								try {
									OggettoCarrello.addItemToCart(articoloV,
											Carrello.recuperaCarrelloByUser(user.getEmail()).getIdCarrello());
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								giaAggiunto = false;
							}
						}

						user.getCarrello().setArticoli(articoliMix);
					}
					direzione = "/orderServlet";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
					dispatcher.forward(request, response);
				} else {
					user.setStato("errore");
					direzione = "/WEB-INF/loginPage.jsp";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
					dispatcher.forward(request, response);
				}
			}
		}else if (action.equals("logout")) {
			if (user.getEmail() != null) {
				System.out.println("logout");
				user.setCognome(null);
				user.setNome(null);
				user.setEmail(null);
				user.setPassword(null);
				user.setCarrello(new Carrello());
				user.setStato("unlogged");
			} else {
				
				//TODO: dare una controllata qui!!!
				Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
				adm.setRuolo(null);
				adm.setUtente(null);
				adm.setStato("unlogged");
			}
			request.getSession().invalidate();
			direzione = "/homepage";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
			dispatcher.forward(request, response);
		}else if(action.equals("admintouser")) {
			Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
			Utente temp = new Utente();
			try {
				temp = Utente.controllaCredenziali(adm.getEmail(), adm.getPassword());
				
				adm.setRuolo(null);
				adm.setUtente(null);
				adm.setStato("unlogged");
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (temp.getEmail() != null) {
				user.setCognome(temp.getCognome());
				user.setNome(temp.getNome());
				user.setEmail(temp.getEmail());
				user.setPassword(temp.getPassword());
				user.setCap(temp.getCap());
				user.setCitta(temp.getCitta());
				user.setVia(temp.getVia());
				user.setCivico(temp.getCivico());
				user.setOrdini(new ArrayList<Ordine>());
				user.setStato("loggato");
				loadCreditCard(user);
				Carrello visitatore = user.getCarrello();
				loadCart(user);

				if (!visitatore.getArticoli().isEmpty()) {
					ArrayList<OggettoCarrello> articoliDaVisitatore = visitatore.getArticoli();
					ArrayList<OggettoCarrello> articoliDaDatabase = new ArrayList<OggettoCarrello>();
					try {
						articoliDaDatabase = OggettoCarrello.recuperaArticoliByCarrello(
								Carrello.recuperaCarrelloByUser(user.getEmail()).getIdCarrello());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					for (OggettoCarrello o : articoliDaDatabase) {
						System.out.println(o.getOggetto().getNome());
					}

					ArrayList<OggettoCarrello> articoliMix = new ArrayList<OggettoCarrello>();

					OggettoCarrello oggettoTemp = new OggettoCarrello();

					boolean giaAggiunto = false;

					for (OggettoCarrello articoloV : articoliDaVisitatore) {

						for (OggettoCarrello articoloD : articoliDaDatabase) {

							if (articoloV.getOggetto().getIdProdotto() == articoloD.getOggetto().getIdProdotto()) {

								oggettoTemp.setOggetto(articoloV.getOggetto());
								oggettoTemp.setQuantita(articoloV.getQuantita() + articoloD.getQuantita());
								System.out.println(oggettoTemp.getQuantita());

								try {
									OggettoCarrello.updateItemQuantity(
											Carrello.recuperaCarrelloByUser(user.getEmail()).getIdCarrello(),
											oggettoTemp.getOggetto().getIdProdotto(), oggettoTemp.getQuantita());
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								articoliMix.add(oggettoTemp);
								giaAggiunto = true;
							} else {
								articoliMix.add(articoloD);
							}
						}
						if (!giaAggiunto) {
							articoliMix.add(articoloV);
							try {
								OggettoCarrello.addItemToCart(articoloV,
										Carrello.recuperaCarrelloByUser(user.getEmail()).getIdCarrello());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							giaAggiunto = false;
						}
					}

					user.getCarrello().setArticoli(articoliMix);
				}
				direzione = "/orderServlet";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
			} else {
				user.setStato("errore");
				direzione = "/WEB-INF/loginPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
				dispatcher.forward(request, response);
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String direzione = null;
		Utente user = (Utente) request.getSession().getAttribute("utente");
		
		Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
		
		if (adm.getEmail() != null) {
			direzione = "/WEB-INF/adminPage.jsp";
		} else if (user.getStato().equals("unlogged") && adm.getStato().equals("unlogged")) {
			direzione = "/WEB-INF/loginPage.jsp";
		} else {
			direzione = "/WEB-INF/customerPage.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direzione);
		dispatcher.forward(request, response);
	}

	private void loadCreditCard(Utente user) {
		try {
			user.setCarte(CartaDiCredito.recuperaCarteByUtente(user.getEmail()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadCart(Utente user) {
		try {
			user.setCarrello(Carrello.recuperaCarrelloByUser(user.getEmail()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}