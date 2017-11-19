	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<html>

	<head>
		<meta charset="utf-8">
		<title>Receipt Recognizer Login</title>

		<!-- Google Fonts -->
		<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>

		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/animate.css">
		<!-- Custom Stylesheet -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/login.css">

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	</head>

	<body>
		<div class="container">
			<div class="top">
				<h1 id="title" class="hidden"><span id="logo">Receipt Recognizer login</span></h1>
			</div>

			<div class="login-box animated fadeInUp">
				<div class="box-header">
					<h2>Log In</h2>
					<%
			String error = (String) request.getAttribute("error");
			if (error != null && error.equals("true")) {
				out.println("<h3 style=\"color:red\">Invalid login credentials. Please try again!!</h3>");
			}
		%>
					<form name='loginForm' action="<c:url value='login' />" method='POST'>
						</div>
						<label for="username">Username</label>
						<br/>
						<input type="text" id="username" name='username'>
						<br/>
						<label for="password">Password</label>
						<br/>
						<input type="password" id="password" name='password'>
						<br/>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<button  name="submit" type="submit">Sign In</button>
						<br/>
					</form>

			</div>
			
		</div>
	</body>

	<script>
		$(document).ready(function () {
			$('#logo').addClass('animated fadeInDown');
			$("input:text:visible:first").focus();
		});
		$('#username').focus(function() {
			$('label[for="username"]').addClass('selected');
		});
		$('#username').blur(function() {
			$('label[for="username"]').removeClass('selected');
		});
		$('#password').focus(function() {
			$('label[for="password"]').addClass('selected');
		});
		$('#password').blur(function() {
			$('label[for="password"]').removeClass('selected');
		});
	</script>

	</html>