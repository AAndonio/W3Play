$("#edit-password").click(
    function() {
        $("#password-div").css("display", "block");
    }
);

$("#account-remove").click(function() {
	$("#remaccount-div").css("display", "block");
});

$("#changerole").click(function() {
	$("#change-div").css("display", "block");
});





$("#edit-address").click(
    function() {
        $("#address-div").css("display", "block");
    }
);

$(".edit-payment").click(
    function() {
        $("#payment-div").css("display", "block");
    }
);

$("#add-product").click(
    function() {
        $("#addproduct-div").css("display", "block");
    }
);



$("#product-edit").click(
	    function() {
	        $("#editproduct-div").css("display", "block");
	    }
);




$(document).mouseup(function(e) 
{
    var contenitore = $("#password-div");

    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
    {
        contenitore.css("display","none");
        contenitore.unbind( 'click', clickDocument );
    }
});

$(document).mouseup(function(e) 
		{
		    var contenitore = $("#change-div");

		    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
		    {
		        contenitore.css("display","none");
		        contenitore.unbind( 'click', clickDocument );
		    }
		});


$(document).mouseup(function(e) 
{
    var contenitore = $("#address-div");

    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
    {
        contenitore.css("display","none");
        contenitore.unbind( 'click', clickDocument );
    }
});

$(document).mouseup(function(e) 
{
    var contenitore = $("#payment-div");

    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
    {
        contenitore.css("display","none");
        contenitore.unbind( 'click', clickDocument );
    }
});

$(document).mouseup(function(e) 
{
    var contenitore = $("#addproduct-div");

    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
    {
        contenitore.css("display","none");
        contenitore.unbind( 'click', clickDocument );
    }
});

$(document).mouseup(function(e) 
		{
		    var contenitore = $("#remproduct-div");

		    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
		    {
		        contenitore.css("display","none");
		        contenitore.unbind( 'click', clickDocument );
		    }
		});

$(document).mouseup(function(e) 
		{
		    var contenitore = $("#remaccount-div");

		    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
		    {
		        contenitore.css("display","none");
		        contenitore.unbind( 'click', clickDocument );
		    }
		});

$(document).mouseup(function(e) 
		{
		    var contenitore = $("#editproduct-div");

		    if (contenitore.is(e.target) && contenitore.has(e.target).length === 0) 
		    {
		        contenitore.css("display","none");
		        contenitore.unbind( 'click', clickDocument );
		    }
		});