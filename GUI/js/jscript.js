$(document).ready(function () {

         var inputs = $(':input').keypress(function (e) {
             if (e.which == 13) {
                 e.preventDefault();
                 var nextInput = inputs.get(inputs.index(this) + 1);
                 if (nextInput) {
                     nextInput.focus();
                 }
             }
         });

     }); 
	 
	 function validate1()
{
var x=document.forms["enquiry"]["name"].value;
var address=document.enquiry.address.value;
var phoneno=document.enquiry.phoneno.value;
var email=document.enquiry.email.value;
if( name == "" || name == null)
{   //checking if the form is empty
       
	document.getElementById('nmerror').innerHTML="*Please enter first name*";
    document.getElementById('name').style.borderColor='red';
		 return false;
                
    }
if( address == "" || address == null)
{   //checking if the form is empty
       
	document.getElementById('adderror').innerHTML="*Please enter address*";
    document.getElementById('address').style.borderColor='red';
		 return false;
                
    }
if( phoneno == "" || phoneno == null)
{   //checking if the form is empty
       
	document.getElementById('pherror').innerHTML="*Please enter phone no*";
    document.getElementById('phoneno').style.borderColor='red';
		 return false;
                
    }	
	 	 
} 
function validate(){ 
var name=document.enquiry.name.value;
var address=document.enquiry.address.value;
var phoneno=document.enquiry.phoneno.value;
var email=document.enquiry.email.value; 


//var vechiclesdate=document.transreg.jdate.value;
 var email2 = /^[a-zA-Z]{8,50}$/;
 var email1= /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})$/;
var phoneno = /^(\d{8,12}){1}$/;

    if( name== "" || name == null){   //checking if the form is empty
       //document.getElementById('nmerror').innerHTML="*Please enter first name*";
 //document.getElementById('name').style.borderColor='red';
	window.alert("Please enter your lname.");
		 return false;
                //displaying a message if the form is empty
    }
	if ((fname.length <5 ) || fname.length > 50) {
          document.getElementById('tferrors').innerHTML="Name should be minimum 5 and maximum 15";
		   document.getElementById('tfirstname').style.borderColor='red solid 5px';
		 return false;
            }
	if( lname== "" || lname== null){
	 document.getElementById('tlerrors').innerHTML="*Please enter a last name*";
	 //window.alert("Please enter your lname.");
	 // document.registration.name.focus();
	   return false;
	}
	if ((lname.length <3 ) || lname.length >10) {
          document.getElementById('tlerrors').innerHTML="Name should be minimum 3 and maximum 10";
		  return false;
            }
	
	if( transid== ""|| transid== null){
	  document.getElementById('tterrors').innerHTML="*Please enter  transportid*";
      return false;
	  
	}
	if (!transid.match(trans))
            {
               document.getElementById('tterrors').innerHTML="*Please enter alphanumeric transportid*";
      return false;
            }
	if(pass1 == ""|| pass1== null){
	  document.getElementById('tperrors').innerHTML="*Please enter  password*";
      return false;
	}
	if (!pass1.match(pass))
            {
               document.getElementById('tperrors').innerHTML="*Please enter 6 charecters.Must contain an upper case letter,a number or sepcial charecter*";
      return false;
            }
	if(pass2 == ""|| pass2== null){
	  document.getElementById('tcerrors').innerHTML="*Please enter  confirm password*";
      return false;
	}
	if((pass1) != (pass2))
   {
  document.getElementById('tperrors').innerHTML = "Password Must be equal";
  document.transreg.pass1.value == "";
  document.transreg.pass2.value == "";
  
  return false;
  }
	if( contactno== "" || contactno ==null ){
	  document.getElementById('tnoerrors').innerHTML="*Please enter  contact no*";
      return false;
	}
	if (!contactno.match(phoneno))
            {
               document.getElementById('tnoerrors').innerHTML="*Please correct phone number*";
      return false;
            }
	
	if(licenseno== "" || licenseno==null){
	  document.getElementById('tlierrors').innerHTML="*Please enter license no*";
      return false;
	}
	if (!licenseno.match(lincese))
            {
               document.getElementById('tlierrors').innerHTML="*Please enter two alphabets, next 1 or 2 digits, next 1 0r 2 alphabets, next 1 to 4 digits*";
      return false;
            }
	
	
	
	
	if( vehiclesRCNo== ""){
	  document.getElementById('tvrerrors').innerHTML="*Please enter  Vehicles RC No*";
	  
      return false;
	}
	if(vechiclesno == ""){
	  document.getElementById('tvnoerrors').innerHTML="*Please enter  Vehicles No*";
	  
      return false;
	}
	
	else
	{
		
	return true;
	
	}
}// JavaScript Document