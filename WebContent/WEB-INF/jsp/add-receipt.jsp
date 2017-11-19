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
				<span id="logo">Add new Receipt	</span>
			</h1>
			<button class="logout"  onclick="javascript:location.href='<%=request.getContextPath()%>/logout'">Logout</button>
			<div class="edit-container">
			<div class="button-container">
				<form method="POST"
					action="${pageContext.request.contextPath}/upload?${_csrf.parameterName}=${_csrf.token}"
					enctype="multipart/form-data">
					<div class="file-upload">
					<div class="form-element">
						<input id="fileName" name="fileName" type="text"/>
					
						
							<label for="file-input">
		        				<span class="button">CHOOSE IMAGE</span>
		    				</label>
							<input type="file" class="file" id="file-input" onchange="document.getElementById('fileName').value = this.value.split('\\').pop().split('/').pop().split('.')[0]" />
						</div>
					<div class="button-container">
						<button onclick="return backToTable();">CANCEL</button>
						<button>ADD</button>
					</div>
					</div>
				</form>
				</div>
			</div>
		</div>
	</div>
	<script>
	backToTable = function(){
		console.log($(location).attr('href'));	
		window.location.href = "${pageContext.request.contextPath}/receipttable";
		return false;;
	}
	</script>
</body>
</html>