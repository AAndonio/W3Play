<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.OggettoCarrello, java.util.Formatter, bean.CartaDiCredito, bean.Utente"%>
    
    <jsp:useBean id="utente" class="bean.Utente" scope="session" />
    
<!DOCTYPE html>
<html>

<head>
  <title> W3Play</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="css/pageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="css/checkoutPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <script src="script/jquery-3.2.1.min.js"></script>
</head>

<body>
  <div id="page">
   <header>
      <div class="header-top">

        <a href="homepage"><img class="logo" src="img/logo2.png" alt="logo"></a>

        <div class="search-form">
            <form id="search-form" action="searchServlet" onsubmit="return validateSearch();"> 
            <input id="search-form-field" name="prodottoCercato" type="search" value="" placeholder="Cerca prodotto..." />
            <button id="search-button" type="submit"><img src="img/search-button.png"> </button>
            <input type="hidden" name="action" value="search"/>
          </form>
        </div>

        <div class="header-top-buttons">
          <div class="header-top-button">
          <img src="img/user.png"><a id="login-link" href="login"> Il mio profilo</a>
           
          </div>
           
          <div class="header-top-button">
        	<img src="img/cart2.png">
            <a href="cartServlet">Carrello</a>
          </div>
        
        </div>
      </div>
    </header>

    <div class="navbar-mobile">
      <div id="menu-image-mobile" onclick="showMenu()">
        <img src="img/menu.png" >
      </div>
        <form id="search-form-mobile" action="searchServlet" onsubmit="return validateSearch();">
          <input id="search-form-field-mobile" name="prodottoCercato" type="search" placeholder="Cerca prodotto...">
          <input type="submit" style="position: absolute; left: -9999px"/>
           <input type="hidden" name="action" value="search"/>
        </form>
      <div id="navbar-mobile-menu">
        <ul>
          <li><a href="login">Login</a></li>
          <li><a href="cartServlet">Carrello</a></li>
          <li><a href="searchServlet?ricercaMenu=si&produttore=Sony&piattaforma=0&nome=PS">Sony</a></li>
          <li><a href="searchServlet?ricercaMenu=si&produttore=Microsoft&piattaforma=0&nome=XBOX">Microsoft</a></li>
          <li><a href="searchServlet?ricercaMenu=si&produttore=Nintendo&piattaforma=0&nome=Nintendo">Nintendo</a></li>
          <li><a href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PC&nome=PS4">PC</a></li>
        </ul>
      </div>
 
    </div>
 
    <nav>
      <ul class="navbar-nav nav-font">
     
        <li class="dropdown nav-link"><a class="nav-link" href="searchServlet?ricercaMenu=si&produttore=Sony&piattaforma=0&nome=PS">Sony</a>
          <div class = "dropdown-menu">
            <div class="nav-dropdown-menu">
              <h6>Console</h6>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=PS4">PS4</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=PS3">PS3</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=PSVita">PSVita</a><br>
            </div>
            <div class="nav-dropdown-menu">
              <h6>Videogiochi</h6>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PS4&nome=0">Giochi PS4</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PS3&nome=0">Giochi PS3</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PSVita&nome=0">Giochi PSVita</a>
            </div>
         </div>
     </li>
        <li class="dropdown"><a class="nav-link" href="searchServlet?ricercaMenu=si&produttore=Microsoft&piattaforma=0&nome=XBOX">Microsoft</a>
          <div class = "dropdown-menu">
            <div class="nav-dropdown-menu">
              <h6>Console</h6>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=XBOX ONE">Xbox One</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=XBOX 360">Xbox 360</a><br>
            </div>
            <div class="nav-dropdown-menu">
              <h6>Videogiochi</h6>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=XBOX ONE&nome=0">Giochi Xbox One</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=XBOX 360&nome=0">Giochi Xbox 360</a>
            </div>
         </div>
        </li>
        <li class="dropdown"><a class="nav-link" href="searchServlet?ricercaMenu=si&produttore=Nintendo&piattaforma=0&nome=Nintendo">Nintendo</a>
            <div class = "dropdown-menu">
            <div class="nav-dropdown-menu">
              <h6>Console</h6>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=Nintendo Switch">Switch</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=Nintendo Wii U">Wii U</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=0&nome=Nintendo 3DS">3DS</a><br>
            </div>
            <div class="nav-dropdown-menu">
              <h6>Videogiochi</h6>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=Switch&nome=0">Giochi Switch</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=Wii U&nome=0">Giochi Wii U</a><br>
              <a class = "nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=3DS&nome=0">Giochi 3DS</a>
            </div>
         </div>
        </li>
        <li class="dropdown"><a class="nav-link" href="searchServlet?ricercaMenu=si&produttore=0&piattaforma=PC&nome=0">PC Games</a>   
        </li>
      </ul>
     
 
    </nav>


    <div class="main-content">
     <%Formatter formatter = new java.util.Formatter();%>
       <form id="checkout-form" action="checkoutServlet" method="post" onsubmit="return validateChangeAddCreditCard();">
            <div id="recap-container">
                <h2>Riepilogo</h2>
                    <ol>
                        <li class="checkout-step">
                            <h3>Indirizzo di spedizione</h3>  
                                <ul class="step-content default-choice">
                                  <li><%=utente.getNome()%> <%=utente.getCognome()%></li>
                                  <li><%=utente.getVia()%>, <%=utente.getCivico()%></li>
                                  <li><%=utente.getCitta()%>, <%=utente.getCap()%></li>
                                </ul>
                        </li>
                        
                        <li class="checkout-step">
                            <h3>Metodo di pagamento</h3>
                            <div class="button-block">
                            <% for(CartaDiCredito carta: utente.getCarte()){ %>
                           		<input id="default-payment<%= carta.getNumerocarta()%>" class="radio-button-default" name="payment" type="radio" value="<%= carta.getNumerocarta()%>">
                            		<label for="default-paymen<%= carta.getNumerocarta()%>t">
                          				<ul class="step-content default-choice">
                                			<li><%= carta.getNumerocarta() %></li>
                                			<li>Scade il <%=carta.getScadenza()%></li>
                            			</ul>
                            		</label>
                            		<br>
                            	<% } %>
	                            <input id="payment-alternative" name="payment" type="radio" value="alternative">
	                           	 	<label class="alternative-label" for="payment-alternative">Utilizza altra carta di credito</label>
	                              		<ul id="payment-form" class="step-content">
	                                  		<li>Titolare <input id="customer-alternative" class="field-form" type="text"></li>
	                                  		<li>Numero carta <input name="credit-card-alternative" id="street-alternative" class="field-form" type="text"></li>
		                                  	<li>CCV <input id="number-alternative" class="field-form" type="text"></li>
		                                  	<li>Scadenza <input id="city-alternative" class="field-form" type="text"></li>
	                              		</ul>
	                              	</div>
                        </li>
                        <li class="checkout-step">
                          <h3>Articoli</h3>
                          <table>
                            <tr>
                              <th>Prodotto</th>
                              <th class="table-row">Q.tà</th>
                              <th class="table-row">Costo &euro;</th>
                            </tr>
                            <% for(OggettoCarrello articolo: utente.getCarrello().getArticoli()){
                            	Formatter formatter2 = new java.util.Formatter();%>
                            <tr>
                              <td><%= articolo.getOggetto().getNome()%></td>
                              <td class="table-row"><%=articolo.getQuantita()%></td>
                              <td class="table-row"><%=formatter2.format("%.2f",articolo.getOggetto().getPrezzo() * articolo.getQuantita())%></td>
                            </tr>
                            <% } %>
                          </table>
                        </li>
                    </ol> 
                
            </div>
            <div id="final-checkout">
                    <h3>Totale ordine: &euro;<%=formatter.format("%.2f", utente.getCarrello().getPrezzoTotale())%></h3>
                    
                        <input id="final-checkout-button" type="submit" value="Inoltra ordine"/>
                       	<input type="hidden" name="action" value="riepilogo"/>
             </div>
                </form>
     </div>

        
    

    <footer>
      <div id="useful-links" class="footer-elements">
        <h4> Link utili </h4>
        <a href="#">F.A.Q.</a><br>
        <a href="#">Termini e condizioni</a><br>
        <a href="#">Contattaci</a><br>
      </div>
      <div id = "payments" class="footer-elements">
        <h4> Paga con </h4>
        <img src="img/payments.png">
      </div>
      <div id="shippings" class="footer-elements">
        <h4> Spediamo con </h4>
        <img class ="footer-img" src="img/bartolini-logo.png">
      </div>
      <div id = "contact-info" class="footer-elements">
        <h3> W3Play Corp. </h3>
        <p> Via delle stelle, 99</p>
        <p>84121, Salerno (SA) </p>
        <p> w3play@gmail.com </p>
      </div>
    </footer>
  

<script>
function showMenu() {
  if(document.getElementById("navbar-mobile-menu").style.display === "none"){
    document.getElementById("navbar-mobile-menu").style.display = "block";
  }
  else
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

	});</script>
</div>
</body>

</html>