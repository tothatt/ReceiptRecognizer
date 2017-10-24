<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_parameter" content="_csrf" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Receipt Recognizer BME - Receipts</title>

<!-- Google Fonts -->
<link
	href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/animate.css">
<!-- Custom Stylesheet -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/table.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>

<body>
	<div class="container">
		<button class="logout" onclick="javascript:location.href='<%=request.getContextPath()%>/logout'">Logout</button>
		<div class="table-box animated fadeInUp">
			<div class="box-header">
				<div class="placeholder"></div>
				<h2>Receipts</h2>
				<button
					onclick="javascript:location.href='<%=request.getContextPath()%>/addreceipt'">Add
					new!</button>
			</div>
			<table style="width: 100%" id="reciepts">

			</table>
		</div>
	</div>


	<script>
	window.onload = function() {
		var jsonResponse;
		var ctx = "${pageContext.request.contextPath}";
	
		
		var token = $("meta[name='_csrf']").attr("content");
		$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
			  jqXHR.setRequestHeader('X-CSRF-Token', token);
			});
		
		$.ajax({
			'url' : ctx + '/allreceiptdata',
			'type' : 'GET',
			'success' : function(data) {
				if (data.errCode) {
		            // data.redirect contains the string URL to redirect to
		            window.location.href = ctx + '/error/' +errorCode;
		        }
				jsonResponse = data;
				insertDataIntoTable();
			},
			'error' : function(request, error) {
				console.log("Request: " + JSON.stringify(request));
			}
		});
		
		$(document).ready(function () {
	        $('#logo').addClass('animated fadeInDown')
	        $('input:text:visible:first').focus()
	    })
	    insertDataIntoTable = function(){
			var header = ['Id','Name','Address','Company','Date','Final value','Action'];
		
	    var columnCount = header.length
	    var table = document.getElementById('reciepts')
	    var row = table.insertRow(-1)
	    for (var i = 0; i < columnCount; i++) {
	        var headerCell = document.createElement('TH')
	        headerCell.innerHTML = header[i]
	        row.appendChild(headerCell)
	    }
	    for (var i = 0; i < jsonResponse.length; i++) {
	        row = table.insertRow(-1)
	       	var cell = row.insertCell(-1);
	        cell.innerHTML = jsonResponse[i][0];
	        cell = row.insertCell(-1);
	        cell.innerHTML = jsonResponse[i][5];
	        cell = row.insertCell(-1);
	        cell.innerHTML = jsonResponse[i][1];
	        cell = row.insertCell(-1);
	        cell.innerHTML = jsonResponse[i][2];
	        cell = row.insertCell(-1);
	        cell.innerHTML = new Date(jsonResponse[i][3]);
	        cell = row.insertCell(-1);
	        cell.innerHTML = jsonResponse[i][4] + ' ft'; 
	       	cell = row.insertCell(-1);
	        cell.setAttribute('class', 'action');
	        var imgSrc = "<%=request.getContextPath()%>/resources/images/edit.jpg";
	        var onclick = "javascript:location.href='<%=request.getContextPath()%>/receiptdetails/"
							+ jsonResponse[i][5] + "'";
					cell.innerHTML = '<img src="' + imgSrc + '" onclick=" ' + onclick + '" alt=\'\'/>'
				}
			};

		}
	</script>
</body>
</html>