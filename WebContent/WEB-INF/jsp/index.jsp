<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
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
	<br>
	<div style="text-align: center">
		<h2>
			Receipt recognizer BME<br> <br>
		</h2>
		<form method="POST" action="${pageContext.request.contextPath}/upload"
			enctype="multipart/form-data">
			<p>
				Name of receipt:<input type="text" name="fileName" />
			</p>
			<p>
				<input type="file" class="file" name="textFile" />
			</p>
			<p>
				<input type="submit" class="button" value="Upload receipt" />
			</p>
		</form>
		<p>
			Name of receipt:<input type="text" name="fileName" id="szamlaNev" />
		</p>
		<button class="button" onclick="processImage()">Process
			receipt</button>
		<p>
			Name of receipt:<input type="text" name="fileName" id="szamlaNevRed" />
		</p>
		<button class="button" onclick="redirectToReceipt()">Load
			receipt by name</button>
	</div>
	<script>
		var ctx = "${pageContext.request.contextPath}";

		var token = $("meta[name='_csrf']").attr("content");
		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			jqXHR.setRequestHeader('X-CSRF-Token', token);
		});

		processImage = function() {
			var name = $("#szamlaNev").val();
			$.ajax({
				'url' : ctx + '/processimage/' + name,
				'type' : 'GET',
				'success' : function() {
					console.log('Receipt updated');
				},
				'error' : function(request, error) {
					console.log('Receipt update failed');
				}
			});
		}

		redirectToReceipt = function() {
			var name = $("#szamlaNevRed").val();
			window.location.href = ctx + '/receipt/' + name;
		}
	</script>
</body>
</html>