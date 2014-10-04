$(document).ready(function() {
				
		if (!($.browser.msie))
		{
			//for the website preloader
			//QueryLoader.init();
			
			//speech related will only run if the browser is non ie
			//for setting the correct width of speech notification div
			var documentwidth=$(document).width();
			var speechinputwidth=$('#speechinput').width();
			var speechnotificationwidth=documentwidth-speechinputwidth-30;
			$("#speechnotification").width(speechnotificationwidth);
			$("#speechnotification").css("left",-(speechnotificationwidth+5));
			
			//checking if html5 speech input is implemented in the browser of not.
			var d=document.getElementById("speech");
			if(!d.onwebkitspeechchange&&!d.onspeechchange)
			{
				var notification= "Voice input functionality is currently not supported in your browser.<br /> Please install the latest version (11+) of Google Chrome to acceess this functionality";
				notify(notification,3000);
				$("#speechinput").click(function(){
					var notification= "Voice input functionality is currently not supported in your browser.<br /> Please install the latest version (11+) of Google Chrome to acceess this functionality";
					notify(notification,3000);								   
				});
			}
			else
			{
				var notification= "Voice input functionality is supported in your browser.<br /> *Valid voice commands are: CHAT, VIDEO, PICTURE, LIVE, CONTACT";
				notify(notification,3000);
			}
		}
		else
		{
			//If browser is IE i will remove speech divs
			$("#speechinput").remove();
			$("#speechnotification").remove();
		}				
	});	

function processspeech()
{
	var speechtext=$("#hello").val();
	setTimeout(do_something,1000);
}

function do_something()
{
	//alert("saad");
	document.getElementById("submit_btn").click()
}

function notify(notification,time)
{
	if (typeof time == 'undefined' ) time = 2000;
	$("#speechnotification").html(notification);
}