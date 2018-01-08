<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.OggettoCarrello , bean.Carrello, bean.Utente, java.util.Formatter, bean.Amministratore"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <title> W3Play</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="css/pageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="<%=request.getContextPath()%>/css/cartPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
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
           
             <% Utente utente = (Utente) request.getSession().getAttribute("utente"); 
             	Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
             if(utente.getStato().equals("loggato") || adm.getEmail() != null){%> <img src="img/user.png"><a id="login-link" href="login"> Il mio profilo</a>
            <%}else{%>  <img src="img/login.svg"><a id="login-link" href="login"> Login</a><%}%>
          </div>
            <% if(adm.getEmail() == null){ %>
          <div class="header-top-button">
        	<img src="img/cart2.png">
            <a href="cartServlet">Carrello</a>
          </div>
         <%} %>
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
        <div id="items-container">
            <h2>Carrello</h2>
             <%Formatter formatter = new java.util.Formatter();
             
             if(!utente.getCarrello().getArticoli().isEmpty()) {%>
            
           
            	<%for(OggettoCarrello articolo: utente.getCarrello().getArticoli()) {%>
	            <div class="item">
	                
	                    <img class="cart-item-img" src="<%=articolo.getOggetto().getImmagine()%>"/>
	                    <div class="cart-item-info">
	                        <h4><%=articolo.getOggetto().getNome()%></h4>
	                        <span>di <%=articolo.getOggetto().getProduttore()%></span><br>
	                        <form action="cartServlet" method="post" class="formine"> 
	                        <button type="submit">Rimuovi</button>
	                        <input type="hidden" name="action" value="removeItem">
	                        <input type="hidden" name="idProdotto" value="<%=articolo.getOggetto().getIdProdotto()%>">
	                        </form>
	                    </div>
	             
	                    
	                    <div style="display:inline-block;" class="column-item">
	                        <br>Prezzo <span style="margin-left:1em;">&euro;<%=articolo.getOggetto().getPrezzo()%></span>
	                                 <br>
	                     <div id="quantita-div"style="margin-top: 2em;">
	                   <span>Quantit&agrave;</span>  
	                   
	                    	
	                  <form style="margin-left:1em; display:inline-block;" class="formine" action="cartServlet" method="post">
	                    	<div class="pulsante" style="display:inline-block;">
		                    	<input style="display:inline-block;" class="quantity-button" type="submit" value="-"/>
		                    
		                    	<input type="hidden" name="action" value="decreaseQuantity"> 
		                    	<input type="hidden" name="idProdotto" value="<%= articolo.getOggetto().getIdProdotto()%>"> 
		                    	<input type="hidden" name="quantita" value="<%= articolo.getQuantita()%>">
		                    </div>
	                   	</form>	
	                    	
	                        <span style="display:inline-block;" id="itemQuantity"><%=articolo.getQuantita()%></span>
	                        
	                        <form class="formine" style="display:inline-block;" action="cartServlet" method="post">
	                        <div class="pulsante"  style="display:inline-block;">
		                    	<input class="quantity-button" type="submit" value="+"/>
		                  		<input type="hidden" name="action" value="addQuantity"> 
		                    	<input type="hidden" name="idProdotto" value="<%= articolo.getOggetto().getIdProdotto()%>">
		                    	<input type="hidden" name="quantita" value="<%= articolo.getQuantita()%>"> 
		                    </div>
	                  </form>
	                    </div>
	                  <br>
	                  </div>
	                    
	                    </div>
	              <% } %>
	         
	    
        </div>
        <div id="cart-checkout">
                <h3>Totale provvisiorio: &euro;<%=formatter.format("%.2f", utente.getCarrello().getPrezzoTotale())%></h3>
                <a href="checkoutServlet">Vai al checkout</a>
        </div>
        <% } else {%>
        <div id="cart-empty">Non ci sono articoli nel carrello.</div>
        <%} %>
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
<script src="script/jquery-3.2.1.min.js"></script>
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