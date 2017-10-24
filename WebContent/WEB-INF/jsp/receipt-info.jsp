<html>

<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_parameter" content="_csrf" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

    <title>Receipt edit page</title>

    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/animate.css">
    <!-- Custom Stylesheet -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/edit.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <div class="top">
        <h1 id="title" class="hidden"><span id="logo">Edit your <br><span>Receipt</span></span></h1>
 		<button class="logout" onclick="javascript:location.href='<%=request.getContextPath()%>/logout'">Logout</button>        <div class="edit-container">
            <div class="img-container">
                <img src="${pageContext.request.contextPath}/images/${szamlanev}" id="image" alt="">
            </div>
            <form action="">
                <div class="form-element"><label for="name">Name</label><input id="name" type="text" readonly/></div>
                <div class="form-element"><label for="company">Company</label><input id="company" type="text" /></div>
                <div class="form-element"><label for="address">Address</label><input id="address" type="text" /></div>
                <div class="form-element"><label for="date">Date</label><input id="date" type="date" /></div>
                <div class="form-element"><label for="finalValue">Final value</label><input id="finalValue" type="text" /></div>
                <div class="button-container">
                    <button onclick="javascript:location.href='/'">CANCEL</button>
                    <button>SAVE</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>

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
				$("#name").val(data.name);
				$("#company").val(data.company);
				$("#address").val(data.address);
				var now = new Date(data.date);
				var day = ("0" + now.getDate()).slice(-2);
				var month = ("0" + (now.getMonth() + 1)).slice(-2);

				var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

				$("#date").val(today);
				$("#finalValue").val(data.finalValue);
			}
		}
	</script>
</html>