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
	<div id="containerDiv">
		<p>Company:
		<div id="company"></div>
		</p>
		<p>Address:
		<div id="address"></div>
		</p>
		<p>Date:
		<div id="date"></div>
		</p>
		<p>Final value:
		<div id="final"></div>
		</p>
		<p></p>
	</div>

	<script>
		window.onload = function() {
			var receipt;
			var ctx = "${pageContext.request.contextPath}";

			var token = $("meta[name='_csrf']").attr("content");
			$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
				jqXHR.setRequestHeader('X-CSRF-Token', token);
			});

			$.ajax({
				'url' : ctx + '/receiptinfo/${szamlanev}',
				'type' : 'GET',
				'success' : function(data) {
					if (data.redirect) {
			            // data.redirect contains the string URL to redirect to
			            window.location.href = ctx + '/error/' + data.errCode;
			        }
					receipt = data;
					printData(data);
				},
				'error' : function(request, error) {
					console.log("Request: " + JSON.stringify(request));
				}
			});

			printData = function(data) {
				$("#company").text(data.company);
				$("#address").text(data.address);
				$("#date").text(new Date(data.date));
				$("#final").text(data.finalValue);
			}
		}
	</script>
</body>
</html>