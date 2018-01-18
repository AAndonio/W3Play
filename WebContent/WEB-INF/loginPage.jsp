<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="bean.Utente, bean.Amministratore"%>
    
<jsp:useBean id="utente" class="bean.Utente" scope="session" />



<!DOCTYPE html>

<html>

<head>
  <title> W3Play</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="css/pageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="css/alertStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="css/loginStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="css/customerPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
  <script src="script/jquery-3.2.1.min.js"></script>
  <script src="script/validationscript.js"></script>

  <!-- ALERT SCRIPT -->
  <script type="text/javascript">
	
  	function hideAlert() {
  		
  		var div = document.getElementsByClassName("alert")[0];
  		div.style.display = "none";
  	}
  
  </script>
  
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
           
          <img src="img/login.svg"><a id="login-link" href="login"> Login</a>
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

     <section id="main-content">
         <div id="login-block">
        	<% if(utente.getStato().equals("errore")) {%>
        		<div>E-mail o password errata</div>
        	<% } %>
        	
        	
       	
        	  <form id="login-form" name = "login-form" action="login" method="post" onsubmit="return validateLogin();">
                <h2>Accedi con i tuoi dati</h2>
                <div class="label-text"><label id="come" for="username">E-mail</label></div>
                <input class="field" name="user-email" id="username"><br>
                <div class="label-text"><label for="password">Password</label></div>
                <input id="password" name="password" class="field" type="password"><br>
                <button type="submit" id="login-button">Login</button>
                <input type="hidden" name="action" value="login"/>
            </form>

            <div id="registration">
                Non sei ancora registrato? <a href="registration.html">Crea un account adesso!</a><br> <a href="#" id="recovery">Password dimenticata?</a>
            </div>
            

         </div>
     </section>
     
      <div id="recovery-div" class="edit-attributes-div">
				<div>
					<form class="form" name="recov" action="RecuperoPassword" method="post" >
           			<h3>Recupera Password</h3>
           			  <label>Username:</label><br/>
                	  <input type="text" id="email" name="email"/><br/>
					  <input class="button-form" type="submit" value="Recupera">
                  	  <input name="action" type="hidden" value="recupera">
              		</form>
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
      
      <!-- ALERT -->
      <%
      	String result = response.getHeader("alert");
      
      	if ("positive".equals(result)) { %>
      		
	      	<div class="alert success">
	  		  <span class="closebtn" onclick="hideAlert()">&times;</span>  
	  		  <strong>Successo!</strong> L'email di recupero è stata inviata con successo.
			</div>
      		
      <% } else if (result != null) { %>
      		
      		<div class="alert error">
      	  	  <span class="closebtn" onclick="hideAlert()">&times;</span>  
      	  	  <strong>Errore!</strong> <%=result %>
      		</div>
      	
      <% } %>
      
    </div>
  <script>
  function showMenu() {
    if(document.getElementById("navbar-mobile-menu").style.display === "none"){
      document.getElementById("navbar-mobile-menu").style.display = "block";
    }
    else
      document.getElementById("navbar-mobile-menu").style.display = "none";
  }
  </script>
 
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

$("#recovery").click(function() {
	$("#recovery-div").css("display", "block");
});

$(document).mouseup(function(e) 
		{
		    var contenitore = $("#recovery-div");

		    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
		    {
		        contenitore.css("display","none");
		        contenitore.unbind( 'click', clickDocument );
		    }
		});

</script>
  </body>
  
  </html>