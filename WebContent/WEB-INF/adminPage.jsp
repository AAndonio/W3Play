<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="bean.Utente, bean.Amministratore"%>
<!DOCTYPE html>
<html>

<head>
    <title> W3Play - Home </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/pageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
    <link href="css/customerPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
    <link href="css/adminPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
    <script src="script/jquery-3.2.1.min.js"></script>
    <script src="script/popupform.js"></script>
    
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">   
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/codemirror.min.css">
 
    <!-- Include Editor style. -->
    <link href="css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
    <link href="css/froala_style.min.css" rel="stylesheet" type="text/css" />
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
           
             <% 
             	Amministratore adm = (Amministratore) request.getSession().getAttribute("admin");
             	Amministratore.Ruolo ruolo = adm.getRuolo();
             %>
             
             <img src="img/user.png"><a id="login-link" href="login"> Il mio profilo</a>
          </div>
     
        </div>
      </div>
    </header>

    <div class="navbar-mobile">
      <div id="menu-image-mobile" onclick="showMenu()">
        <img src="img/menu.png" >
      </div>
        <form id="search-form-mobile" action="searchServlet" onsubmit="return validateSearch();">
          <input id="search-form-field-mobile" type="search" placeholder="Cerca prodotto...">
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
            <div id="user-name"><%=adm.getEmail()%></div>
            <div class="scheda1">
              <div class="data"><span>E-mail</span><br><div class="user-value"><%=adm.getEmail()%></div>
              </div>
              <div class="data"><span>Password</span><br><div class="user-value">***</div>
              <img id="edit-password" src="img/edit.png"> </div>
            </div>
			
			<%
				if (ruolo.equals(Amministratore.Ruolo.Backoffice)) { %>
            	<a id="add-product" href="#">Aggiungi prodotto</a><%if(request.getAttribute("risultato") != null) { %><span> <%= request.getAttribute("risultato") %><%} %></span>
            
            <% } else { %>
            	<a id="#statistiche" href="/W3Play/StatisticheServlet">Statistiche</a>
            <% } %>
			
			<form id="form-logout" id="logout-form" action="login" method="post">
          		<button  class="button-customer-page" type="submit" id="logout-button">Logout</button>
            	<input type="hidden" name="action" value="logout"/>
         	</form>
         	<button id="account-remove" class="button-customer-page"> Elimina Account</button>
         	<button id="changerole" class="button-customer-page">Cambia Ruolo</button>
          </div>
           
        </div>
        
         <div id="remaccount-div" class="edit-attributes-div">
				<div>
					<form class="form" name="change" action="editProfileInfo" method="post" >
           			<h3>Elimina Utente?</h3>
					<input class="button-form" type="submit" value="Conferma">
                  	<input name="action" type="hidden" value="removeUtenteA">
              		</form>
				</div>
			</div>
		
		 <!-- CAMBIA RUOLO: admin_to_user -->
		 <div id="change-div" class="edit-attributes-div">
				<div>
					<form class="form" name="change" action="login" method="post" >
           			<h3>Seleziona ruolo:</h3>
					<input class="button-form" type="submit" value="Utente">
                  	<input name="action" type="hidden" value="admintouser">
              		</form>
				</div>
			</div>
       
			
          <div id="password-div"  class="edit-attributes-div">
              <div><form class="form" action = "editProfileInfo" method="post" id="password">
                <h3>Cambia Password</h3>
                  <label>Vecchia Password:</label>
                  <br/>
                  <input type="text" id="old-password"/><br/>
                  <br/>
                  <label>Nuova Password: <span>*</span></label>
                  <br/>
                  <input type="text" name="new-password1" id="new-password1"/><br/>
                  <br/>
                  <label>Conferma nuova password: <span>*</span></label>
                  <br/>
                  <input type="text" name="new-password2" id="new-password2"/><br/>
                  <input class="button-form" type="submit" value="Aggiorna">
                  <input type="hidden" name="action" value="modificaPasswordA"/>
              </form></div>
            </div>
               <div id="addproduct-div" class="edit-attributes-div">
              <div><form class="form" action="productServlet" name="addProduct" id="addproduct" method="post" enctype = "multipart/form-data" onsubmit="return validateAddProduct();">
                <h3>Aggiungi prodotto</h3>
                  <label>Nome</label>
                  <br/>
                  <input type="text" id="product-name" name="product-name" required/>
                  <br/>
                  <label>Produttore<span>*</span></label>
                  <br/>
                  <input type="text" id="product-producer" name="product-producer" required/><br/>
                   <label>Piattaforma<span>*</span></label>
                  <br/>
                  <input type="text" id="product-platform" name="product-platform"/><br/>
                   <label>Genere<span>*</span></label>
                  <br/>
                  <input type="text" id="product-genre" name="product-genere"/><br/>
                  <br/>
                  <label>Descrizione<span>*</span></label>
                  <br/>
                  <textarea id="product-description" name="product-description"/></textarea><br/>
                  <br/>
                  <label>Immagine<span>*</span></label>
                  <br/>
                  <input type = "file" multiple="false" size = "50" name="product-image" required/><br/>
                   <label>Prezzo<span>*</span></label>
                  <br/>
                  <input type="text" id="product-image" name="product-price" required/><br/>
                  <label>Disponibilit&aacute;<span>*</span></label>
                  <br/>
                  <input type="number" id="disponibili" name="disponibili" required/><br/>
                  <label>Data Uscita<span>*</span></label>
                  <br/>
                  <input type="text" id="product-release-date" name="product-release-date" required/><br/>
                  <label>Link Video<span>*</span></label>
                  <br/>
                  <input type="text" id="product-linkVideo" name="product-linkVideo"/><br/>
                  <input class="button-form" type="submit" value="Aggiorna">
                  <input type="hidden" name="action" value="addObject"/>
                  <input type="hidden" name="text"/>
              </form></div>
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

function aggiungiDescrizione(){
	
}

</script>
<script src="script/popupform.js"></script>
<script src="script/validationscript.js"></script>
<script type="text/javascript">
$("#account-remove").click(function() {
	$("#remaccount-div").css("display", "block");
});
</script>

</body>

</html>