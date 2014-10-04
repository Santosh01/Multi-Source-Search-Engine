$(document).ready(function () {
$.backstretch("images/five.jpg");
});

var message="Sorry, right-click has been disabled";
///////////////////////////////////
function clickIE() {if (document.all) {(message);return false;}}
function clickNS(e) {if
(document.layers||(document.getElementById&&!document.all)) {
if (e.which==2||e.which==3) {(message);return false;}}}
if (document.layers)
{document.captureEvents(Event.MOUSEDOWN);document.onmousedown=clickNS;}
else{document.onmouseup=clickNS;document.oncontextmenu=clickIE;}
document.oncontextmenu=new Function("return false")


// Date and Time

//show & hide
$(document).ready(function(){
  
  $("#name").click(function(){
    $("#nm").show();
	 });
	 $("#address").click(function(){
	$("#nm").hide();
	 });
	  $("#phoneno").click(function(){
		$("#nm").hide();
	});	
	 $("#email").click(function(){
	$("#nm").hide();
	
 });
 $("#details").click(function(){
	$("#nm").hide();
	
 });	
});
$(document).ready(function(){
  
  $("#phoneno").focus(function(){
    $("#ph").show();
	 });
	 $("#name").click(function(){
	$("#ph").hide();
	 });
	  $("#address").click(function(){
		$("#ph").hide();
	});	
	 $("#email").click(function(){
	$("#ph").hide();
	
 });
 $("#details").click(function(){
	$("#ph").hide();
	
 });	
});
$(document).ready(function(){
  
  $("#address").focus(function(){
    $("#adds").show();
	 });
	 $("#name").click(function(){
	$("#adds").hide();
	 });
	  $("#phoneno").click(function(){
		$("#adds").hide();
	});	
	 $("#email").click(function(){
	$("#adds").hide();
	
 });
 $("#details").click(function(){
	$("#adds").hide();
	
 });	
});
$(document).ready(function(){
  
  $("#email").focus(function(){
    $("#em").show();
	 });
	 $("#name").click(function(){
	$("#em").hide();
	 });
	  $("#address").click(function(){
		$("#em").hide();
	});	
	 $("#phoneno").click(function(){
	$("#em").hide();
	
 });
  $("#details").click(function(){
	$("#em").hide();
	
 });	
});

