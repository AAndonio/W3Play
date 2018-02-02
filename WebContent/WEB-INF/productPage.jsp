<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="bean.Prodotto, bean.Utente, bean.Amministratore, java.util.Formatter"%>



<!DOCTYPE html>
<html>

<head>
<title>W3Play</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="css/pageStyle.css" type="text/css" rel="stylesheet"
	media="screen" />
<link href="css/productPageStyle.css" type="text/css" rel="stylesheet"
	media="screen" />
<link href="css/customerPageStyle.css" type="text/css" rel="stylesheet"
	media="screen" />
<link href="css/adminPageStyle.css" type="text/css" rel="stylesheet"
	media="screen" />
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet">
<script src="script/jquery-3.2.1.min.js"></script>
<script src="script/popupform.js"></script>
</head>

<body>
	<div id="page">
		<header>
			<div class="header-top">

				<a href="homepage"><img class="logo" src="img/logo2.png"
					alt="logo"></a>

				<div class="search-form">
					<form id="search-form" action="searchServlet"
						onsubmit="return validateSearch();">
						<input id="search-form-field" name="prodottoCercato" type="search"
							value="" placeholder="Cerca prodotto..." />
						<button id="search-button" type="submit">
							<img src="img/search-button.png">
						</button>
						<input type="hidden" name="action" value="search" />
					</form>
				</div>

				<div class="header-top-buttons">
					<div class="header-top-button">

						<%
							Utente utente = (Utente) request.getSession().getAttribute("utente");
							Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
							if (utente.getStato().equals("loggato") || adm.getEmail() != null) {
						%>
						<img src="img/user.png"><a id="login-link" href="login">
							Il mio profilo</a>
						<%
							} else {
						%>
						<img src="img/login.svg"><a id="login-link" href="login">
							Login</a>
						<%
							}
						%>
					</div>
					<%
						if (adm.getEmail() == null) {
					%>
					<div class="header-top-button">
						<img src="img/cart2.png"> <a href="cartServlet">Carrello</a>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</header>

		<div class="navbar-mobile">
			<div id="menu-image-mobile" onclick="showMenu()">
				<img src="img/menu.png">
			</div>
			<form id="search-form-mobile" action="searchServlet"
				onsubmit="return validateSearch();">
				<input id="search-form-field-mobile" name="prodottoCercato"
					type="search" placeholder="Cerca prodotto..."> <input
					type="submit" style="position: absolute; left: -9999px" /> <input
					type="hidden" name="action" value="search" />
			</form>
			<div id="navbar-mobile-menu">
				<ul>
					<li><a href="login">Login</a></li>
					<li><a href="cartServlet">Carrello</a></li>
					<li><a
						href="searchServlet?ricercaMenu=si&produttore=Sony&piattaforma=0&nome=PS">Sony</a></li>
					<li><a
						href="searchServlet?ricercaMenu=si&produttore=Microsoft&piattaforma=0&nome=XBOX">Microsoft</a></li>
					<li><a
						href="searchServlet?ricercaMenu=si&produttore=Nintendo&piattaforma=0&nome=Nintendo">Nintendo</a></li>
					<li><a
						href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PC&nome=PS4">PC</a></li>
				</ul>
			</div>

		</div>

		<nav>
			<ul class="navbar-nav nav-font">

				<li class="dropdown nav-link"><a class="nav-link"
					href="searchServlet?ricercaMenu=si&produttore=Sony&piattaforma=0&nome=PS">Sony</a>
					<div class="dropdown-menu">
						<div class="nav-dropdown-menu">
							<h6>Console</h6>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=PS4">PS4</a><br>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=PS3">PS3</a><br>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=PSVita">PSVita</a><br>
						</div>
						<div class="nav-dropdown-menu">
							<h6>Videogiochi</h6>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PS4&nome=0">Giochi
								PS4</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PS3&nome=0">Giochi
								PS3</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PSVita&nome=0">Giochi
								PSVita</a>
						</div>
					</div></li>
				<li class="dropdown"><a class="nav-link"
					href="searchServlet?ricercaMenu=si&produttore=Microsoft&piattaforma=0&nome=XBOX">Microsoft</a>
					<div class="dropdown-menu">
						<div class="nav-dropdown-menu">
							<h6>Console</h6>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=XBOX ONE">Xbox
								One</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=XBOX 360">Xbox
								360</a><br>
						</div>
						<div class="nav-dropdown-menu">
							<h6>Videogiochi</h6>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=XBOX ONE&nome=0">Giochi
								Xbox One</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=XBOX 360&nome=0">Giochi
								Xbox 360</a>
						</div>
					</div></li>
				<li class="dropdown"><a class="nav-link"
					href="searchServlet?ricercaMenu=si&produttore=Nintendo&piattaforma=0&nome=Nintendo">Nintendo</a>
					<div class="dropdown-menu">
						<div class="nav-dropdown-menu">
							<h6>Console</h6>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=Nintendo Switch">Switch</a><br>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=Nintendo Wii U">Wii
								U</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=Nintendo 3DS">3DS</a><br>
						</div>
						<div class="nav-dropdown-menu">
							<h6>Videogiochi</h6>
							<a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=Switch&nome=0">Giochi
								Switch</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=Wii U&nome=0">Giochi
								Wii U</a><br> <a class="nav-link"
								href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=3DS&nome=0">Giochi
								3DS</a>
						</div>
					</div></li>
				<li class="dropdown"><a class="nav-link"
					href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PC&nome=0">PC
						Games</a></li>
			</ul>


		</nav>

		<section id="main-content">
			<%
				Formatter formatter = new java.util.Formatter();
				Prodotto prodotto = (Prodotto) request.getSession().getAttribute("prodotto");
			%>
			<div id="user-site-position">
				Ti trovi Home > Prodotti >
				<%=prodotto.getGenere()%></div>
			<div id="product">
				<div id="product-header">
					<h3 id="product-platform"><%=prodotto.getPiattaforma()%></h3>
					<h1 id="product-title"><%=prodotto.getNome()%></h1>
				</div>
				<img id="product-image" src="<%=prodotto.getImmagine()%>"
					alt="Gioco ">
				<div id="aside-product-image">
					<div id="buying-options">
						<div id="product-price">
							&euro;<%=formatter.format("%.2f", prodotto.getPrezzo())%></div>
						<%
							if (prodotto.getDisponibilita() <= 0) {
						%>
						<div id="availability" style="color: red">Questo prodotto
							non è disponbile!</div>
						<%
							} else {
						%>
						<div id="availability">Questo prodotto è disponbile!</div>
						<%
							if (adm.getEmail() == null) {
						%>
						<form id="buying-form" method="post" action="cartServlet">
							<label for="qta" id="label-quantity">Quantit&agrave;:</label> <input
								id="quantity-input" name="quantita" type="number" value="1"
								min="1" max="10">
							<button type="submit" class="product-addToCart">
								aggiungi al carrello</button>
							<input type="hidden" name="action" value="addItem" />
						</form>
						<%
							}
							}
							if (adm.getEmail() != null) {
						%>
						<a href="#" id="product-edit"> Modifica Prodotto</a> <a href="#"
							id="product-remove"> Elimina Prodotto</a>
						<%
							if (request.getAttribute("risultato") != null) {
						%><span> <%=request.getAttribute("risultato")%></span>
						<%
							}
							}
						%>
					</div>

					<div id="product-information" class="info">
						<h3>Informazioni sul prodotto</h3>
						<ul id="informations">
							<li><span class="information-type">Anno di
									pubblicazione:</span> <%=prodotto.getDataUscita()%></li>
							<li><span class="information-type">Produttore:</span><%=prodotto.getProduttore()%></li>
							<li><span class="information-type">Genere:</span><%=prodotto.getGenere()%></li>
						</ul>
					</div>
				</div>

				<div id="product-description">
					<h2>Descrizione</h2>
					<p><%=prodotto.getDescrizione()%></p>
				</div>
				<%
					if (prodotto.getLinkVideo() != null && !prodotto.getLinkVideo().equals("")) {
				%>
				<div id="product-trailer">
					<h2>Video</h2>
					<iframe src="<%=prodotto.getLinkVideo()%>" width="100%"
						height="80%" allowfullscreen> </iframe>
				</div>
				<%
					}
				%>
			</div>

			<div style="align-content: center;" id="addproduct-div"
				class="edit-attributes-div">
				<div>
					<form class="form" action="productServlet" name="addProduct"
						id="addproduct" method="post" enctype="multipart/form-data"
						onsubmit="return validateAddProduct();">
						<h3>Modifica prodotto</h3>
						<label>Nome</label> <br /> <input type="text" id="product-name"
							value="<%=prodotto.getNome()%>" name="product-name" required />
						<br /> <label>Produttore<span>*</span></label> <br /> <input
							type="text" id="product-producer"
							value="<%=prodotto.getProduttore()%>" name="product-producer"
							required /><br /> <label>Piattaforma<span>*</span></label> <br />
						<input type="text" id="product-pla"
							value="<%=prodotto.getPiattaforma()%>" name="product-platform" required /><br />
						<label>Genere<span>*</span></label> <br /> <input type="text"
							id="product-genre" value="<%=prodotto.getGenere()%>"
							name="product-genere" /><br /> <br /> <label>Descrizione<span>*</span></label>
						<br />
						<textarea id="product-descr" name="product-description" /></textarea>
						<br /> <br /> <label>Immagine<span>*</span></label> <br /> <input
							type="file" multiple="false" size="50" name="product-image"
							 /><br /> <label>Prezzo<span>*</span></label> <br /> <input
							type="text" id="product-prezzo"
							value="<%=prodotto.getPrezzo()%>" name="product-price" required  /><br />
						<label>Disponibilit&aacute;<span>*</span></label> <br /> <input
							type="text" id="disponibili"
							value="<%=prodotto.getDisponibilita()%>" name="disponibili"
							required /><br /> <label>Data Uscita<span>*</span></label> <br />
						<input type="text" id="product-release-date"
							value="<%=prodotto.getDataUscita()%>"
							name="product-release-date" required /><br /> <label>Link
							Video<span>*</span>
						</label> <br /> <input type="text" id="product-linkVideo"
							value="<%=prodotto.getLinkVideo()%>" name="product-linkVideo" required /><br />
						<input class="button-form" type="submit" value="Aggiorna">
						<input type="hidden" name="action" value="modificaProdotto" /> <input
							type="hidden" name="idProdotto"
							value="<%=prodotto.getIdProdotto()%>" /> <input type="hidden"
							name="text" />
					</form>
				</div>
			</div>

			<div style="align-content: center;" id="remproduct-div"
				class="edit-attributes-div">
				<div>
					<form class="form" action="productServlet" method="post"
						enctype="multipart/form-data">
						<h3>Rimuovi Prodotto?</h3>
						<input class="button-form" type="submit" value="Conferma">
						<input type="hidden" name="action" value="removeProdotto" /> <input
							type="hidden" name="idProdotto"
							value="<%=prodotto.getIdProdotto()%>" /> <input type="hidden"
							name="text" />
					</form>
				</div>
			</div>

		</section>

		<footer>
			<div id="useful-links" class="footer-elements">
				<h4>Link utili</h4>
				<a href="#">F.A.Q.</a><br> <a href="#">Termini e condizioni</a><br>
				<a href="#">Contattaci</a><br>
			</div>
			<div id="payments" class="footer-elements">
				<h4>Paga con</h4>
				<img src="img/payments.png">
			</div>
			<div id="shippings" class="footer-elements">
				<h4>Spediamo con</h4>
				<img class="footer-img" src="img/bartolini-logo.png">
			</div>
			<div id="contact-info" class="footer-elements">
				<h3>W3Play Corp.</h3>
				<p>Via delle stelle, 99</p>
				<p>84121, Salerno (SA)</p>
				<p>w3play@gmail.com</p>
			</div>
		</footer>
	</div>
	<script>
		function showMenu() {
			if (document.getElementById("navbar-mobile-menu").style.display === "none") {
				document.getElementById("navbar-mobile-menu").style.display = "block";
			} else
				document.getElementById("navbar-mobile-menu").style.display = "none";
		}
	</script>
	<script src="script/validationscript.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$('#search-form-field-mobile').keydown(function(event) {
				// enter has keyCode = 13, change it if you want to use another button
				if (event.keyCode == 13) {
					this.form.submit();
					return false;
				}
			});

		});

		$("#product-edit").click(function() {
			$("#addproduct-div").css("display", "block");
		});
		$("#product-remove").click(function() {
			$("#remproduct-div").css("display", "block");
		});
	</script>
</body>

</html>