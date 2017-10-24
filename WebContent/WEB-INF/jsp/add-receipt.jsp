<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_parameter" content="_csrf" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<!-- Google Fonts -->
<link
	href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/animate.css">
<!-- Custom Stylesheet -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/new.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<title>Add new receipt</title>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<div class="container" style="text-align: center">
		<div class="top">
			<h1 id="title" class="hidden">
				<span id="logo">Add new <br>
				<span>Receipt</span></span>
			</h1>
			<button class="logout"  onclick="javascript:location.href='<%=request.getContextPath()%>/logout'">Logout</button>
			<div class="edit-container">
				<form method="POST"
					action="${pageContext.request.contextPath}/upload?${_csrf.parameterName}=${_csrf.token}"
					enctype="multipart/form-data">
					<div class="form-element">
						<label for="fileName">Name</label><input id="fileName" name="fileName" type="text" />
					</div>
					<div class="form-element">
						<input type="file" class="file" name="textFile" onchange="document.getElementById('fileName').value = this.value.split('\\').pop().split('/').pop().split('.')[0]" />
					</div>
					<div class="button-container">
						<button>CANCEL</button>
						<button>UPLOAD</button>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>