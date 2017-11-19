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
<div class="container">
	<div class="top">
		<h1 id="title" class="hidden">
			<span id="logo">Edit <br> <span>Receipt</span></span>
		</h1>
		<button class="logout" onclick="javascript:location.href='<%=request.getContextPath()%>/logout'">Logout</button>		<div class="new-container">
			<div class="img-container">
				<canvas id="canvas"></canvas>
				<img style="display: none;" id="receipt" alt="image"
					src="${pageContext.request.contextPath}/images/${szamlanev}" /> <img
					id="output" />
				<div class="display">
				<input type="range" name="points" id="imageSize" value="50" min="1" max="100" onchange="sizeChange();">
				</div>
				<div class="display">
					<button onclick="printRects();">SHOW</button>
					<button onclick="hideRects();">HIDE</button>
					<button onclick="updateXml();">UPDATE</button>
					<button onclick="return backToTable();">CANCEL</button>
				</div>
			</div>
			<div class="actions">
				<img class="arrow"
					src="<%=request.getContextPath()%>/resources/images/arrow.png"
					id="load" alt="" onclick="loadText();">
			</div>
			<div class="txt-area">
				<textarea id="textFromReceipt"></textarea>
			</div>
		</div>
	</div>
</div>

<script>
	window.onload = function() {
		var jsonResponse;
		var ctx = "${pageContext.request.contextPath}";
		var canvas = document.getElementById('canvas'); /// canvas element
		var canvasCtx = canvas.getContext('2d');
		var imgWidth = 100;
		var imgHeight = 100;
		var smaller = $("#imageSize").val();
		var img = document.getElementById('receipt'); /// canvas element
		var jsonObject;
		var receipt;
		var width;
		var height;
		
		var token = $("meta[name='_csrf']").attr("content");
		$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
			  jqXHR.setRequestHeader('X-CSRF-Token', token);
			});
		
		$.ajax({
			'url' : ctx + '/imageinfo/${szamlanev}',
			'type' : 'GET',
			'success' : function(data) {
				if (data.errCode) {
		            // data.redirect contains the string URL to redirect to
		            window.location.href = ctx + '/error/' + data.errCode;
		        }
				receipt = data;
				printRects(data);
			},
			'error' : function(request, error) {
				console.log("Request: " + JSON.stringify(request));
			}
		});
		printRects = function(json) {
			width = receipt.imgWidth*(smaller/100);
			height = receipt.imgHeight*(smaller/100);
			canvasCtx.canvas.width = width;
			canvasCtx.canvas.height = height;
			
			canvasCtx.drawImage(img, 0,0, width, height);
			jsonObject = receipt.chars;
			for (var i = 0; i < jsonObject.length; i++) {
				canvasCtx.rect(jsonObject[i].l*(smaller/100), jsonObject[i].t*(smaller/100), (jsonObject[i].r*(smaller/100)-jsonObject[i].l*(smaller/100)), (jsonObject[i].b*(smaller/100)-jsonObject[i].t*(smaller/100)));
				if(jsonObject[i].suspicious){
					canvasCtx.strokeStyle = "red";
					canvasCtx.fillStyle = 'red';}
				else{
					canvasCtx.strokeStyle = "black";
					canvasCtx.fillStyle = 'blue';}
				canvasCtx.stroke();
				var fontSize = Math.round(50 *(smaller/100));
				canvasCtx.font=fontSize+"px Time New Roman";
				canvasCtx.fillText(jsonObject[i].s,jsonObject[i].l*(smaller/100), jsonObject[i].b*(smaller/100));
			}
		}
		$("#canvas").click(function(e){
			for (var i = 0; i < jsonObject.length; i++) {
				  var x = e.pageX - this.offsetLeft;
				  var y = e.pageY - this.offsetTop;
				if (jsonObject[i].l*(smaller/100) < x && jsonObject[i].r*(smaller/100) > x && jsonObject[i].b*(smaller/100) > y && jsonObject[i].t*(smaller/100) < y) {
					var newchar = prompt("New value for char: ", jsonObject[i].s);
					if(newchar !== jsonObject[i].s && newchar != null && newchar != undefined) {
						jsonObject[i].s = newchar;
						printRects();
						}
				}
			}
		});
		
		updateXml = function(){
			$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
				  jqXHR.setRequestHeader('X-CSRF-Token', token);
				});
			$.ajax({
				'url' : ctx + '/changechar',
				'type' : 'POST',
				'contentType': 'application/json;charset=UTF-8',
				'data' : JSON.stringify(receipt),
				'success' : function() {
					console.log('Receipt updated');
					location.reload();
				},
				'error' : function(request, error) {
					console.log('Receipt update failed');
				}
			});
		}
		
		sizeChange = function(){
			smaller = $("#imageSize").val();
			printRects();
		}
		
		hideRects = function(){
			canvasCtx.drawImage(img, 0,0, width, height);
		}
		textAreaAdjust = function(o) {
		    o.style.height = "1px";
		    o.style.height = (25+o.scrollHeight)+"px";
		}
		
		loadText = function(){
			 $('#textFromReceipt').text('');
			var textJson = receipt.lines;
			 for (var key in textJson) {
			      if (textJson.hasOwnProperty(key)) {
			    	  $('#textFromReceipt').append(textJson[key]+ '\n');
			      }
			    }
			 textAreaAdjust(document.getElementById("textFromReceipt"));
		}
		backToTable = function(){
			console.log($(location).attr('href'));	
			window.location.href = "${pageContext.request.contextPath}/receipttable";
			return false;;
		}
	}
	</script>
</body>
</html>