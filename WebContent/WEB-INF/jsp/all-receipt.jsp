<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_parameter" content="_csrf" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Receipt Recognizer BME</title>
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}

canvas {
	border: 1px solid #d3d3d3;
}

.button {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 20px;
}
</style>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	
		<p id = "data">
			
		</p>


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
				$("#data").html(JSON.stringify(data));
			},
			'error' : function(request, error) {
				console.log("Request: " + JSON.stringify(request));
			}
		});
		
		
	}
	</script>
</body>
</html>