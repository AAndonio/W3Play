function validateForm(){
	
	var nome = document.forms["registrazione"]["user-name"].value;
	var cognome = document.forms["registrazione"]["user-surname"].value;
	var email = document.forms["registrazione"]["user-email"].value;
	var password = document.forms["registrazione"]["password"].value;
	var password2 = document.forms["registrazione"]["password2"].value;
	var via = document.forms["registrazione"]["user-via"].value;
	var civico = document.forms["registrazione"]["user-civico"].value;
	var cap = document.forms["registrazione"]["user-cap"].value;
	var citta = document.forms["registrazione"]["user-citta"].value;
	
	
	if (!onlyLetterWithSpace(nome)){
		alert("Il nome puo' contenere solo caratteri alfabetici");
		return false;
	}else if (!onlyLetterWithSpace(cognome)){
		alert("Il cognome puo' contenere solo caratteri alfabetici");
		return false;
	}else if (!validateEmail(email)){
		alert("L'indirizzo e-mail non e' nel formato corretto");
		return false;
	}else if (!alphanumeric(password)){
		alert("La password deve contenere lettere e numeri");
		return false;
	}else if (password != password2) {
		alert("Le due password inserite non corrispondono");
		return false;
	}else if (!onlyLetterWithSpace(via)) {
		alert("La via deve contenere solo lettere!");
		return false;
	}else if (!numeric(civico)) {
		alert("Il civico deve essere un numero");
		return false;
	}else if(!numeric(cap)) {
		alert("Il CAP deve essere numerico");
		return false;
	}else if (!onlyLetterWithSpace(citta)) {
		alert("La Citta' deve contenere solo lettere");
		return false;
	}else if (!document.getElementById('agreement').checked){
		alert("I termini e le condizioni devono essere accettate");
		return false;
	}else{
		return true;
    }
		
}
	

	function onlyLetter(parola) {
		var letters = new RegExp(/^[A-Za-z]+$/);
		return letters.test(parola);
	}
	function onlyLetterWithSpace(parola) {
		var letters = new RegExp(/^[A-Za-z ]{5,10}$/);
		return letters.test(parola);
	}

	function alphanumeric(uadd)
	{
		var letters = new RegExp(/^[0-9a-zA-Z]+$/);					
		return letters.test(uadd);
	}
	
	function alphanumericLength(uadd)
	{
		var letters = new RegExp(/^[0-9a-zA-Z :'-_!"£$%&\\()=?^]{6,20}$/);					
		return letters.test(uadd);
	}
	
	function alphanumericSpace(uadd)
	{
		var letters = new RegExp(/^[0-9a-zA-Z ]+$/);
		return letters.test(uadd);
	}

	  function Date(date)
	    {
	        var letters = new RegExp(/^[0-9]{4}\-[0-9]{2}\-[0-9]{2}$/);
	        return letters.test(date);
	    }
	   
	    function numeric(uadd)
	    {
	        var letters = new RegExp(/^[0-9]+$/);
	        return letters.test(uadd);
	    }
	   
	    function Ccv(ccv)
	    {
	        var letters = new RegExp(/^[0-9]{3}$/);
	        return letters.test(ccv);
	    }

	function validateEmail(uemail)
	{
		var letters = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
	    return letters.test(uemail);
	}

	function CreditCard(card)
	{
		var letters = new RegExp(/^[0-9]{4}\s[0-9]{4}\s[0-9]{4}\s[0-9]{4}$/);
		return letters.test(card);
	}
	
	function linkVideo(url)
	{
		var urlReg = /[-a-zA-Z0-9@:%_\+.~#?&//=]{2,256}\.[a-z]{2,4}\b(\/[-a-zA-Z0-9@:%_\+.~#?&//=]*)?/gi;
		return urlReg.test(url);
	}

	function isEmpty(field){
	    if(field.value.length <= 0)
			return true;
	    else
			return false;
	}
	
	

	function validateChangePassword(passVecchia)
	{
		var oldPass = document.forms["changePass"]["old-password"].value;
		var newPass1 = document.forms["changePass"]["new-password1"].value;
		var newPass2 = document.forms["changePass"]["new-password2"].value;
		console.log(newPass1);
	
		if(!(oldPass == passVecchia)){
			alert("La password inserita non corrisponde a quella precedente!");
			return false;
		}else if (!alphanumeric(newPass1)){
			alert("La password deve contenere lettere e numeri");
			return false;
		}else if (!(newPass1==newPass2)) {
			alert("Le due password inserite non corrispondono");
			return false;
		}
		
	}
	
	function validateChangeAddress()
	{
		var via = document.forms["changeAddress"]["Via"].value;
		var numCivico = document.forms["changeAddress"]["NumeroCivico"].value;
		var CAP = document.forms["changeAddress"]["Cap"].value;
		var citta = document.forms["changeAddress"]["Citta"].value;
		if (!onlyLetterWithSpace(via)) {
			alert("La via deve contenere solo lettere!");
			return false;
		}else if (!numeric(numCivico)) {
			alert("Il civico deve essere un numero");
			return false;
		}else if(!numeric(CAP)) {
			alert("Il CAP deve essere numerico");
			return false;
		}else if (!onlyLetter(citta)) {
			alert("La Citta'á deve contenere solo lettere");
			return false;
		}
	}
	
    function validateChangeCreditCard()
    {
        var numCarta = document.forms["changeCreditCard"]["numeroCarta"].value;
        var Titolare = document.forms["changeCreditCard"]["Titolare"].value;
        var Scadenza = document.forms["changeCreditCard"]["Scadenza"].value;
        var ccv = document.forms["changeCreditCard"]["ccv"].value;
       
        if(!CreditCard(numCarta)) {
            alert("La carta di credito inserita non è valida(ES: 1234 1234 1234 1234)");
            return false;
        }else if (!onlyLetterWithSpace(Titolare)){
            alert("Il titolare puo' contenere solo caratteri alfabetici");
            return false;
        }else if (!Date(Scadenza)){
            alert("La data inserita non è corretta");
            return false;
        }else if (!Ccv(ccv)){
            alert("il ccv non è valido!");
            return false;
        }
       
    }
    
    function validateSearch()
    {
    	var prodottoCercato = document.forms["search-form"]["prodottoCercato"].value;
    	if(!alphanumericSpace(prodottoCercato))
    		{
    		alert("Il prodotto cercato inserito non è valido");
    		return false;
    		}
    }
    
    function validateAddProduct() {
    	var nome = document.forms["addProduct"]["product-name"].value;
    	var produttore = document.forms["addProduct"]["product-producer"].value;
    	var piattaforma = document.forms["addProduct"]["product-platform"].value;
    	var genere = document.forms["addProduct"]["product-genere"].value;
    	var descrizione = document.forms["addProduct"]["product-description"].value;
    	var prezzo = document.forms["addProduct"]["product-price"].value;
    	var disponibilita = document.forms["addProduct"]["disponibili"].value;
    	var dataUscita	= document.forms["addProduct"]["product-release-date"].value;
    	var video = document.forms["addProduct"]["product-linkVideo"].value;
    	
      	if(!alphanumericSpaceWithSpecial(nome)){
    		alert("Il nome del gioco deve contenere solo lettere e numeri");
    		return false;
    	}else if (!alphanumericSpaceWithSpecial(produttore)){
    		alert("Il Produttore puo essere solo alfanumerico");
    		return false;
    	}else if (piattaforma != "" && !alphanumericSpaceWithSpecial(piattaforma)){
    		alert("Il nome della piattaforma può essere solo alfanumerico");
    		return false;
    	}else if(genere != "" && !alphanumericSpaceWithSpecial(genere)){
    		alert("Il genere inserito non  e' corretto");
    		return false;
    	}else if(descrizione != "" && !alphanumericSpace(descrizione)){
    		alert("La descrizione deve essere alfanumerica!");
    		return false;
    	}else if (!price(prezzo)){
    		alert("Il prezzo inserito non e' valido!");
    		return false;
    	}else if (!(numeric(disponibilita)) || disponibilita < "0"){
    		alert("Il numero di prodotti disponibili non è valido!");
    		return false;
    	}else if(!Date(dataUscita)){
    		alert("La data inserita non è nel formato corretto (aaaa/mm//gg)");
    		return false;
    	}else if(video != "" && !linkVideo(video)){
    		alert("L'URL del video non è corretto");
    		return false;
    	}
    	
    }
    
    function alphanumericSpaceWithSpecial(uadd)
	{
		var letters = new RegExp(/^[0-9a-zA-Z :'-_!"£$%&\\()=?^]{3,20}$/);
		return letters.test(uadd);
	}
    
    function price(uadd)
	{
		var letters = new RegExp(/^[0-9/.]+$/);
		return letters.test(uadd);
	}
    
    function validateLogin() {
    	
    	var email = document.forms["login-form"]["user-email"].value;
    	var pass = document.forms["login-form"]["password"].value;
    	
    	
    	if (!validateEmail(email)){
    		alert("L'indirizzo e-mail non e' nel formato corretto");
    		return false;
    	}else if (!alphanumericLength(pass)){
    		alert("La password deve contenere tra 6 e 20 caratteri");
    		return false;
    	}
    }
    
    function validateChangeAddCreditCard()
    {
    	if(document.getElementById["payment-alternative"].checked)
    		return validateChangeCreditCard();
    	else
    		return true;
    	}
    

    
    