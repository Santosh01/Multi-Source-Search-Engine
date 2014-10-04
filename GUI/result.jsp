<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Search</title>
<link href="images/icon.ico" rel="shortcut icon">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="content/styles.css" rel="stylesheet" />
<link href="css/Pagination.css" rel="stylesheet" />
<link href="css-jsp/ui-lightness/jquery-ui-1.8.23.custom.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.8.2jsp.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.customjsp.js"></script>
<script type="text/javascript" src="js/jqueryjsp.js"></script>
<script type="text/javascript" src="js/myscripts.js"></script>
<script type="text/javascript" src="js/date_time.js"></script>
<script type="text/javascript" src="js/Pagination.js"></script>
</script>
<script>
	
	
	
$(function() {
$( "#tabs" ).tabs();
});
//==========================================Query Processing for Wikipedia=====================================//
var numb = 0;
		function success_fn() {
		var output = "";
		var inp = document.form1.query.value;
		inp = inp.replace("","+");
		var url = "";
		if(inp != "") {
			url = "http://localhost:8080/solr/Wikipedia/select?q="+inp+"&defType=edismax&qf=title^10&qf=text^5&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3CFONT%20COLOR=%22blue%22%3E%3Cb%3E&hl.simple.post=%3C%2Fb%3E%3C/FONT%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000&fl=score"; 
			
			//"http://localhost:8080/solr/Wikipedia/select?q="+inp+"&defType=edismax&qf=title^10&qf=text^5&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000&fl=score"; 
			
			//"http://localhost:8080/solr/Wikipedia/select?q=title:"+inp+"&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000";

			$.getJSON( url, {
				format: "json"
			}).done(function(data) {
				
				output += '<ul>';
// === response ==== //
			if(data.response != "") {
					output += '<ul>';
					output += '<li style="font-size:15px;font-style:arial; color:#CC9900; text-align:right">Number of Document Found: ' + data.response.numFound + '</li> <br>';
				}

				if(data.response.docs != "") {
					$.each(data.response.docs, function(key, val) {
						output += '<ul>';

						if(val.title !== undefined)	
							output += '<li style="font-size:20px;font-style:arial; color:#2E64FE">' + val.title + '</li>';
						if(val.url !== undefined)
							output += '<li>'+'<a href="'+val.url+'" target="_blank">'+  val.url +'</a>'+'</li>';
//===== highlighting end ==== //
						if(data.highlighting !=""){
							var tempArray = data.highlighting;
							var x ="";
							for(var i in tempArray){
								if(i == val.id)
									if(tempArray[i].text != "" && tempArray[i].text !== undefined)
										//alert(tempArray[i].text);
										x = x + "&nbsp&nbsp&nbsp&nbsp..."+tempArray[i].text + "...";
										
							}							
							output += x;
							output += '</ul>';
						}
//===== highlighting end ==== //

					});
				}

				
				output +='</ul>';
				$('#update #update_inside').html(output);
				numb = $('#update_inside ul ul ul').size();

				$('#update_inside ul ul ul').hide();
				$('#update_inside ul ul ul').slice(0, 9).show();
				$('#update_inside ul ul ul').css("margin-bottom","40px");		
$('#page').pagination({
			items: numb,
			itemsOnPage: 9,
			cssStyle: 'light-theme'
		});

			});
		} else {
			alert("Please Enter some id in input text field...");
		}
	}

	$(document).ready(function(){
		success_fn();
  																
		
		return false;
		
	});

function clicking() {
			var index = $('#page').pagination('getCurrentPage');
			var upper=index*9;
			var lower=upper-9;
			$('#update_inside ul ul ul').hide();
			$('#update_inside ul ul ul').slice(lower, upper).show();
		}


	
//==========================================Query Processing for Wikivoyage=========================================//
	function success_fn2() {
		var output = "";
		var inp = document.form1.query.value;
		inp = inp.replace("","+");
		var url = "";
		if(inp != "") {
			url = "http://localhost:8080/solr/Wikivoyage/select?q="+inp+"&defType=edismax&qf=title^10&qf=text^5&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3CFONT%20COLOR=%22blue%22%3E%3Cb%3E&hl.simple.post=%3C%2Fb%3E%3C/FONT%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000&fl=score"; 
			
			//"http://localhost:8080/solr/Wikivoyage/select?q="+inp+"&defType=edismax&qf=title^10&qf=text^5&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000&fl=score"; 
			
			//"http://localhost:8080/solr/Wikivoyage/select?q="+inp+"&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000";
			

			$.getJSON( url, {
				format: "json"
			}).done(function(data) {
				
				output += '<ul>';
// === response ==== //
			if(data.response != "") {
					output += '<ul>';
					output += '<li style="font-size:15px;font-style:arial; color:#CC9900; text-align:right">Number of Document Found: ' + data.response.numFound + '</li> <br>';
				}

				if(data.response.docs != "") {
					$.each(data.response.docs, function(key, val) {
						output += '<ul>';

						if(val.title !== undefined)	
							output += '<li style="font-size:20px;font-style:arial; color:#2E64FE">' + val.title + '</li>';
						if(val.url !== undefined)
							output += '<li>'+'<a href="'+val.url+'" target="_blank">'+  val.url +'</a>'+'</li>';
//===== highlighting end ==== //
						if(data.highlighting !=""){
							var tempArray = data.highlighting;
							var x ="";
							for(var i in tempArray){
								if(i == val.id)
									if(tempArray[i].text != "" && tempArray[i].text !== undefined)
										//alert(tempArray[i].text);
										x = x + "&nbsp&nbsp&nbsp&nbsp..."+tempArray[i].text + "...";
										
							}							
							output += x;
							output += '</ul>';
						}
//===== highlighting end ==== //

					});
				}

				
				output +='</ul>';
				$('#update2 #update_inside2').html(output);
				numb = $('#update_inside2 ul ul ul').size();

				$('#update_inside2 ul ul ul').hide();
				$('#update_inside2 ul ul ul').slice(0, 9).show();
				$('#update_inside2 ul ul ul').css("margin-bottom","40px");		
$('#page2').pagination({
			items: numb,
			itemsOnPage: 9,
			cssStyle: 'light-theme'
		});

			});
		} else {
			alert("Please Enter some id in input text field...");
		}
	}

	$(document).ready(function(){
		success_fn2();
  																
		
		return false;
		
	});

function clicking2() {
			var index = $('#page2').pagination('getCurrentPage');
			var upper=index*9;
			var lower=upper-9;
			$('#update_inside2 ul ul ul').hide();
			$('#update_inside2 ul ul ul').slice(lower, upper).show();
		}



//==========================================Query Processing for Wikinews=========================================//

	function success_fn3() {
		var output = "";
		var inp = document.form1.query.value;
		inp = inp.replace("","+");
		var url = "";
		if(inp != "") {
			url = "http://localhost:8080/solr/Wikinews/select?q="+inp+"&defType=edismax&qf=title^10&qf=text^5&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3CFONT%20COLOR=%22blue%22%3E%3Cb%3E&hl.simple.post=%3C%2Fb%3E%3C/FONT%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000&fl=score"; 
			
			//"http://localhost:8080/solr/Wikinews/select?q="+inp+"&defType=edismax&qf=title^10&qf=text^5&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000&fl=score";  
			
			//"http://localhost:8080/solr/Wikinews/select?q="+inp+"&wt=json&indent=true&fl=id+title+url&hl=true&hl.fl=text&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E&hl.highlightMultiTerm=true&hl.requireFieldMatch=true&rows=1000";

			$.getJSON( url, {
				format: "json"
			}).done(function(data) {
				
				output += '<ul>';
// === response ==== //
			if(data.response != "") {
					output += '<ul>';
					output += '<li style="font-size:15px;font-style:arial; color:#CC9900; text-align:right">Number of Document Found: ' + data.response.numFound + '</li> <br>';
				}

				if(data.response.docs != "") {
					$.each(data.response.docs, function(key, val) {
						output += '<ul>';

						if(val.title !== undefined)	
							output += '<li style="font-size:20px;font-style:arial; color:#2E64FE">' + val.title + '</li>';
						if(val.url !== undefined)
							output += '<li>'+'<a href="'+val.url+'" target="_blank">'+  val.url +'</a>'+'</li>';
//===== highlighting end ==== //
						if(data.highlighting !=""){
							var tempArray = data.highlighting;
							var x ="";
							for(var i in tempArray){
								if(i == val.id)
									if(tempArray[i].text != "" && tempArray[i].text !== undefined)
										//alert(tempArray[i].text);
										x = x + "&nbsp&nbsp&nbsp&nbsp..."+tempArray[i].text + "...";
										
							}							
							output += x;
							output += '</ul>';
						}
//===== highlighting end ==== //

					});
				}

				
				output +='</ul>';
				$('#update3 #update_inside3').html(output);
				numb = $('#update_inside3 ul ul ul').size();

				$('#update_inside3 ul ul ul').hide();
				$('#update_inside3 ul ul ul').slice(0, 9).show();
				$('#update_inside3 ul ul ul').css("margin-bottom","40px");		
$('#page3').pagination({
			items: numb,
			itemsOnPage: 9,
			cssStyle: 'light-theme'
		});

			});
		} else {
			alert("Please Enter some id in input text field...");
		}
	}

	$(document).ready(function(){
		success_fn3();
  																
		
		return false;
		
	});

function clicking3() {
			var index = $('#page3').pagination('getCurrentPage');
			var upper=index*9;
			var lower=upper-9;
			$('#update_inside3 ul ul ul').hide();
			$('#update_inside3 ul ul ul').slice(lower, upper).show();
		}
	
</script>

<!==========================================HTML Start=======================================================>

</head>
<body>
<div align="center">

	<div id="header">
    	<div class="container">
            <a target="_blank" href="http://www.buffalo.edu"><div class="logo"></div></a>
            <div class="dt">
                <span id="date_time"></span>
                <script type="text/javascript">window.onload = date_time('date_time');</script>
            </div>
    	</div><!-- end of container -->
    </div><!-- end of header -->

    <div id='cssmenu'>
    <div class="container">
        <ul>
           <li><a href='index.html'><span>Home</span></a></li>
            <li class='last'><a href='http://localhost/phpsqlajax_map_v3.html'><span>Map</span></a></li>
           <li><a href='about1.html'><span>About Project</span></a></li>
           <li class='last'><a href='contact.html'><span>Contact Us</span></a></li>
           <li class='last'><a href='faq.html'><span>FAQ</span></a></li>
        </ul>
        	
	<div class="search1">
    	<div class="container">
        	<div id="search-bar">
          
            <form method="get" action="result.jsp" id="form1">
           <input type="text" name="query" class="search" id="hello" value="Search here" onFocus="if(this.value == 'Search here') {this.value = '';this.style.color='black';}" >
            <input type="submit" class="button" value="Search" name="submit_btn" id="submit_btn">
            
            </form>
            </div><!--end of search bar-->
            <div id="space"></div>
        </div><!--end of container-->
    </div><!--search1-->
    </div><!--end of container-->
    </div><!--end of cssmenu-->
         
    
    <!-- Content -->
    <div class="container">
    <div class="content">
    <form name="form1" >
    	<input type="hidden" name="query" value="<% out.println(request.getParameter("query")); %>">
    </form> 
    	<h2>Results for:&nbsp;   <span><% out.println(request.getParameter("query")); %></span></h2>
		<div id="tabs">
            <ul>
                <li><a href="#tabs-1" id="one">Wikipedia </a></li>
                <li><a href="#tabs-2" id="two">Wikivoyage</a></li>
                <li><a href="#tabs-3" id="three">Wikipedia News</a></li>
            </ul>
            <div id="tabs-1">
            	<div id="update">
			<div id="update_inside"></div>
			<div style="width:100%; text-align: center;"><div id="page" style="display: inline-block" onclick="clicking();"></div></div>
		</div>
            </div>
            <div id="tabs-2">
            	<div id="update2">
            	
            		<div id="update_inside2"></div>
			<div style="width:100%; text-align: center;"><div id="page2" style="display: inline-block" onclick="clicking2();"></div></div>
		</div>
            </div>
            <div id="tabs-3">
            	<div id="update3">
        	    		<div id="update_inside3"></div>
		<div style="width:100%; text-align: center;"><div id="page3" style="display: inline-block" onclick="clicking3();"></div></div>
		</div>
            </div>
        </div>
    </div>
    </div>
    
    <!-- Footer -->
    <div id="footer">
    <div class="container">
        <div class="footer_nav">
          <span>Copyright &copy; 2014, <a href="http://www.buffalo.edu">University at Buffalo </a> All rights reserved | <a href="privacy.html"> Privacy & Accessibility </a></span></div>
        <div class="footer_nav _center">
            <a target="_blank" href="https://www.facebook.com/universityatbuffalo"><img src="images/s1.png"></a>
            <a target="_blank" href="https://twitter.com/UBCommunity"><img src="images/s2.png"></a>
        </div>
        <div class="footer_nav _right">
        	<a href="faq.html">FAQ</a>
        </div>
    </div>
	</div> <!-- end of Footer -->       

</div>
</body>
</html>
