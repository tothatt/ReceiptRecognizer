<html>
<meta charset="UTF-8">
<head>
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
	<div id="containerDiv">
		<canvas id="canvas"></canvas>
		<img style="display: none;" id="receipt" alt="image"
			src="${pageContext.request.contextPath}/images/${szamlanev}" />
			<button class="button"  onclick="hideRects();" >Hide rectangles and recognized chars</button> 	
			<button class="button"  onclick="printRects();" >Show rectangles and recognized chars</button> 	
			
		</div>
		<div>
		<p>Image size adjust: (lower the number bigger it gets): <input id="imageSize" type="text" value = "2" size="4"/>
		</p>
		<p>To update xml file with changed values of characters  lick the button:</p><p>
		<button class="button"  onclick="sizeChange();" >Reload picture</button> 
		<button class="button"  onclick="updateXml();" >Update XML</button></p>
		</div>
	
	<script>
	window.onload = function() {
		var jsonResponse;
		var ctx = "${pageContext.request.contextPath}";
		var canvas = document.getElementById('canvas'); /// canvas element
		var canvasCtx = canvas.getContext('2d');
		var imgWidth = 100;
		var imgHeight = 100;
		var smaller = 2;
		var img = document.getElementById('receipt'); /// canvas element
		var jsonObject;
		var receipt;
		var width;
		var height;
		
		$.ajax({
			'url' : ctx + '/imageinfo/${szamlanev}',
			'type' : 'GET',
			'success' : function(data) {
				receipt = JSON.parse(data);
				printRects(data);
			},
			'error' : function(request, error) {
				alert("Request: " + JSON.stringify(request));
			}
		});
		printRects = function(json) {
			width = receipt.imgWidth/smaller
			height = receipt.imgHeight/smaller
			canvasCtx.canvas.width = width;
			canvasCtx.canvas.height = height;
			
			canvasCtx.drawImage(img, 0,0, width, height);
			jsonObject = receipt.chars;
			for (var i = 0; i < jsonObject.length; i++) {
				canvasCtx.rect(jsonObject[i].l/smaller, jsonObject[i].t/smaller, (jsonObject[i].r/smaller-jsonObject[i].l/smaller), (jsonObject[i].b/smaller-jsonObject[i].t/smaller));
				if(jsonObject[i].suspicious){
					canvasCtx.strokeStyle = "red";
					canvasCtx.fillStyle = 'red';}
				else{
					canvasCtx.strokeStyle = "black";
					canvasCtx.fillStyle = 'blue';}
				canvasCtx.stroke();
				var fontSize = Math.round(50 / smaller);
				canvasCtx.font=fontSize+"px Time New Roman";
				canvasCtx.fillText(jsonObject[i].s,jsonObject[i].l/smaller, jsonObject[i].b/smaller);
			}
		}
		$("#canvas").click(function(e){
			for (var i = 0; i < jsonObject.length; i++) {
				  var x = e.pageX - this.offsetLeft;
				  var y = e.pageY - this.offsetTop;
				if (jsonObject[i].l/smaller < x && jsonObject[i].r/smaller > x && jsonObject[i].b/smaller > y && jsonObject[i].t/smaller < y) {
					var newchar = prompt("New value for char: ", jsonObject[i].s);
					if(newchar !== jsonObject[i].s && newchar != null && newchar != undefined) {
						jsonObject[i].s = newchar;
						printRects();
						}
				}
			}
		});
		
		updateXml = function(){
			$.ajax({
				'url' : ctx + '/changechar',
				'type' : 'POST',
				'contentType': 'application/json;charset=UTF-8',
				'data' : JSON.stringify(receipt),
				'success' : function() {
					console.log('Receipt updated');
					printRects();
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
		
	}
	</script>
</body>
</html>