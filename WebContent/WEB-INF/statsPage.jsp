<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="bean.Utente, bean.Amministratore, bean.Statistiche"%>

<!DOCTYPE html>
<html>
<head>
	<title> W3Play - Statistiche </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/pageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
    <link href="css/adminPageStyle.css" type="text/css" rel="stylesheet" media="screen"/>
    <script src="script/jquery-3.2.1.min.js"></script>
    <script src="script/popupform.js"></script>
    
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">   
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="css/codemirror.min.css">
    
    <!-- Include Editor style. -->
    <link href="css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
    <link href="css/froala_style.min.css" rel="stylesheet" type="text/css" />
    
    <!-- Google Chart -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script> 
 	<script type="text/javascript">

		google.charts.load('current', {'packages':['corechart']});
		
		function drawChart1() {
	  		
	  		var div = document.getElementById('chart-1')
	  		var sells = parseInt(div.getAttribute("sells"))
	  		var earn = parseFloat(div.getAttribute("earnings"))
	  		
		    var data = google.visualization.arrayToDataTable([
		      ['', 'Prodotti', 'Guadagno'],
		      ['Totale', sells,  earn],
		    ]);
		
		    var options = {
		      title: 'Vendite e guadagno mensile',
		      chartArea: {width: '50%'},
		      hAxis: {title: '', minValue: 0 },
		      vAxis: { title: '' },
	    	  colors: ['#325D88', '#E69138'],
	    	  backgroundColor: '#fafafa',
		    };
		
		    var chart = new google.visualization.BarChart(div);
		    chart.draw(data, options);
	  	}
	  	
	  	function drawChart2() {
	  		
	  		var div = document.getElementById('chart-2')
	  		var num = parseInt(div.getAttribute("products"))
	  		
	        var data = google.visualization.arrayToDataTable([
		        ['', 'Prodotti'],
		        ['Totale', 817],
		    ]);
		
		      var options = {
		        title: 'N° prodotti nel catalogo',
		        chartArea: {width: '50%'},
		        hAxis: { title: '', minValue: 0 },
		        vAxis: { title: '' },
		        backgroundColor: '#fafafa'
		      };
	   	
	   	    var chart = new google.visualization.BarChart(div);
	   	    chart.draw(data, options);
	  	}
	  	
	  	function drawChart3() {
	  		
	  		var div = document.getElementById('chart-3')
	  		var usr = parseInt(div.getAttribute("users"))
	  		var avg = parseFloat(div.getAttribute("avg"))
	  		
	  		var data = google.visualization.arrayToDataTable([
		      ['', 'Utenti Registrati', 'Avg ordini'],
		      ['Totale', usr,  avg],
		    ]);
		
		    var options = {
		      title: 'N° utenti registrati e media ordini',
		      chartArea: {width: '50%'},
		      hAxis: {title: '', minValue: 0 },
		      vAxis: { title: '' },
	    	  colors: ['#325D88', '#E69138'],
	    	  backgroundColor: '#fafafa',
		    };
		
		    var chart = new google.visualization.BarChart(div);
		    chart.draw(data, options);
	  	}
	  	
	  	function drawAll() {
	  		
	  		console.log("grafici")
				
			drawChart1()
			drawChart2()
			drawChart3()
	  }
	</script>
	
	<!-- CSS -->
	<style>
		section {
		    left: 0;
		    padding: 2em;
		    top: 8em;
		    width: inherit;
		    height: 100%;
		    display: block;
		}
		
		.chart {
			max-width: 55em !important;
			max-height: 55em !important;
			margin: auto;
		}
		
		rect, svg {
			max-width: 55em !important;
			max-height: 55em !important;
		}
		
		.title {
			font-size: 2em;
			color: rgb(80, 80, 80)
		}
		
		hr {
    		border: 0;
    		height: 0;
		   	border-top: 1px solid rgba(0, 0, 0, 0.4);
		    padding-bottom: 1em;
    	}
	</style>
</head>

<body onload="javascript:drawAll()">
	<div id="page">
    
    <!-- HEADER -->
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
             	Amministratore adm = (Amministratore) session.getAttribute("admin");
             	
             	Statistiche stats = (Statistiche) session.getAttribute("statistiche");
        		System.out.println(stats);
             %>
             
             <a id="changerole" href="#" style="margin-right:1.5em;">Cambia Ruolo</a><img src="img/user.png"><a id="login-link" href="login"> Il mio profilo</a>
         	
          </div>
     
        </div>
      </div>
    </header>
 	
 	<!-- NAV-BAR -->   
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
    
    <!-- PAGE CONTENT -->
    <div class="main-content">
    	<section>
		 <div class="title">Prodotti venduti nell'ultimo mese</div>
		 <hr>
		 <div id="chart-1" class="chart" sells="<%=stats.getNumeroProdottiVenduti() %>" earnings="<%=stats.getGuadagnoUltimoMese() %>"></div>
		 
		 <div class="title">N° prodotti nel catalogo</div>
		 <hr> 
		 <div id="chart-2" class="chart" products="<%=stats.getNumeroProdottiCatalogo() %>"></div>
			
		<div class="title">Utenti registrati e media ordini</div>
		<hr>
		<div id="chart-3" class="chart" users="<%=stats.getNumeroUtentiRegistrati() %>" avg="<%=stats.getAverageOrdiniUtente() %>"></div>
	</section>
    </div>
    
    <!-- FOOTER -->
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
</html>