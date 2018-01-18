<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="bean.CartaDiCredito, java.util.ArrayList, bean.Ordine, model.OggettoOrdine, java.util.Formatter"%>
    
<jsp:useBean id="utente" class="bean.Utente" scope="session" />


<!DOCTYPE html>

<html>

<head>
  <title> W3Play</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="css/pageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <script src="script/jquery-3.2.1.min.js"></script>
  <script src="script/popupform.js"></script>
  <link href="css/customerPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
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
        <div class="scheda">
          <div id="profile-img"><img src="img/utente.png"></div>
          <div id="info">
            <div id="user-name"><%= utente.getNome()%> <%= utente.getCognome()%></div>
            <div class="scheda1">
              <div class="data"><span>E-mail</span><br><div class="user-value"><%= utente.getEmail()%></div>
              </div>
              <div class="data"><span>Password</span><br><div class="user-value">***</div>
              <img id="edit-password" src="img/edit.png" onclick="setHiddenValuePassword('modificaPassword');"></div>
            </div>
            <div class="scheda1">
              <h3> Il tuo indirizzo </h3>
              <ul class="data">
                <li><%= utente.getNome()%> <%= utente.getCognome()%></li>
                <li><%= utente.getVia() %>,<%= utente.getCivico()%></li>
                <li><%= utente.getCitta()%>, <%= utente.getCap() %><img id="edit-address" src="img/edit.png" onclick="setHiddenValueAddress('modificaIndirizzo');"></li>
                
              </ul>
            </div>
            <div class="scheda1">
              <h3 style="display:inline-block"> Le tue carte di credito </h3>
              <%for(CartaDiCredito c:utente.getCarte()){ %>
              <ul class="data">
              	<li> <%=c.getNumerocarta()%></li>
                <li><%=c.getTitolare()%></li>
                <li>Scade il <%=c.getScadenza()%> <img class="edit-payment" src="img/edit.png" onclick="setHiddenValuePayment('<%=c.getNumerocarta()%>', 'aggiornaCarta');"></li>
              </ul>
              <%}%>
              <input class="button-customer-page edit-payment" type="button" value="Aggiungi Carta" onclick="setHiddenValuePayment('','aggiungiCarta');" >
			</div>
			
			<form id="logout-form" action="login" method="post">
          		<button class="button-customer-page" type="submit" id="logout-button">Logout</button>
            	<input type="hidden" name="action" value="logout"/>
         	</form>
         		<button id="account-remove" class="button-customer-page"> Elimina Account</button>
         	</div>
         
         	 <div id="remaccount-div" class="edit-attributes-div">
				<div>
					<form class="form" name="change" action="editProfileInfo" method="post" >
           			<h3>Elimina Utente?</h3>
					<input class="button-form" type="submit" value="Conferma">
                  	<input name="action" type="hidden" value="removeUtente">
              		</form>
				</div>
			</div>
          

          <div id="customer-orders">
            <h2> I tuoi ordini </h2>
            <div class="orders">
				<% 	ArrayList<Ordine> ordini = utente.getOrdini();
				if(ordini != null) {
					for (Ordine o: ordini) { 
					 Formatter formatter = new java.util.Formatter();%>
              <div class="order">
                <div class="order-info">
                  <span class="information order-number">Ordine n° <%= o.getIdOrdine() %></span>
                  <span class="information order-date">del <%=o.getDataAcquisto() %></span>
                  <span class="information order-total">Totale ordine: &euro; <%=formatter.format("%.2f", o.getCosto())%></span>
                </div>
                <div class="order-items">
                  <% 
                  ArrayList<OggettoOrdine> oggOrdine = o.getArticoli();
                 
                  if(oggOrdine != null) {	
                  for (OggettoOrdine ogg: oggOrdine) { %>
                  	<div class="item">
                    <img src="<%=ogg.getProdotto().getImmagine() %>"><h5><%= ogg.getProdotto().getNome() %></h5>
                     </div>
                    <%} 
                   }else System.out.println("ciao");%>
                 
                </div>
                </div>
               <%}
			  } else {%><span class ="information order-number">Non ci sono ordini effettuati</span><%} %>

          </div>
          
         
          
          <div id="password-div" class="edit-attributes-div">
              <div><form class="form" name="changePass" action="editProfileInfo" id="password" method="post" onsubmit="return validateChangePassword('<%=utente.getPassword()%>');">
                <h3>Cambia Password</h3>
                  <label>Vecchia Password:</label>
                  <br/>
                  <input type="text" id="old-password"/><br/>
                  <br/>
                  <label>Nuova Password: <span>*</span></label>
                  <br/>
                  <input type="text" name="new-password1"  id="new-password1"/><br/>
                  <br/>
                  <label>Conferma nuova password: <span>*</span></label>
                  <br/>
                  <input type="text" id="new-password2"/><br/>
                  <input class="button-form" type="submit" value="Aggiorna">
                  <input id="azioneFormPassword" name="action" type="hidden" >
              </form></div>
            </div>

            <div id="address-div" class="edit-attributes-div">
              <div><form name="changeAddress" class="form" action="editProfileInfo" id="address" method="post" onsubmit="return validateChangeAddress();">
                <h3>Cambia indirizzo</h3>
                  <label>Via</label>
                  <br/>
                  <input name="via" type="text" id="Via"/>
                  <br/>
                  <label> Numero civico</label>
                  <br/>
                  <input name="numeroCivico" type="text" id="NumeroCivico"/>
                  <br/>
                  <label>C.A.P.</label>
                  <br/>
                  <input name="cap" type="text" id="Cap"/><br/>
                   <label>Citt&agrave; </label>
                  <br/>
                  <input name="citta" type="text" id="Citta"/><br/>
                  <input class="button-form" type="submit" value="Aggiorna">
                  <input id="azioneFormIndirizzo" name="action" type="hidden" >
              </form></div>
            </div>

              <div id="payment-div" class="edit-attributes-div">
              <div><form name="changeCreditCard" class="form" action="editCartInfo" id="payment" method="post" onsubmit="return validateChangeCreditCard();">
                <h3>Aggiorna metodo di pagamento</h3>
                  <label>Numero carta</label>
                  <br/>
                  <input type="text" id="numeroCarta" name="numeroCarta" placeholder="1234 1234 1234 1234"/>
                  <br/>
                  <label>Titolare</label>
                  <br/>
                  <input type="text" id="Titolare" name="Titolare"/>
                  <br/>
                  <label>Scadenza</label>
                  <br/>
                  <input type="text" id="Scadenza" name="Scadenza"/><br/>
                   <label>C.C.V.</label>
                  <br/>
                  <input type="text" id="ccv" name="ccv"/><br/>
                  <input class="button-form" type="submit" value="Aggiorna">
                  <input id="cartaDaAggiornare" name="cartaDaAggiornare" type="hidden">
                  <input id="azioneFormPagamento" name="action" type="hidden" >
              </form></div>
            </div>
        </div>       
   </div>   
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
  </div>
</body>
<script>
function showMenu() {
  if(document.getElementById("navbar-mobile-menu").style.display === "none"){
    document.getElementById("navbar-mobile-menu").style.display = "block";
  }
  else
    document.getElementById("navbar-mobile-menu").style.display = "none";
}
</script>
<script>
function setHiddenValuePayment(carta, azione) {
  document.getElementById("cartaDaAggiornare").value = carta;
  document.getElementById("numeroCarta").value = carta;
  document.getElementById("azioneFormPagamento").value = azione;
  console.log(carta);
  }
  
  function setHiddenValueAddress(azione) {
	  document.getElementById("azioneFormIndirizzo").value = azione;
	  console.log(azione);
  }
 
  function setHiddenValuePassword(azione) {
	  document.getElementById("azioneFormPassword").value = azione;
	  console.log(azione);
  }
</script>
<script src="script/popupform.js"></script>
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
	</script>


</html>