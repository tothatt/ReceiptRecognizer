<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_parameter" content="_csrf" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Receipt Recognizer BME</title>
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}

.button {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
}
</style>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<button class="logout" onclick="javascript:location.href='<%=request.getContextPath()%>/logout'">Logout</button>
	<br>
	<div style="text-align: center">
		<h2>
			Something went wrong<br> <br>
		</h2>
		<h3>
			${error.errCode} , ${error.errMsg} <br> <br>
		</h3>
	
	</div>
	<script>
		
	</script>
</body>
</html>