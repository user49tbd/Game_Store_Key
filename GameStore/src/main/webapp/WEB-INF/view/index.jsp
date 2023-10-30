<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
@font-face{
	font-family: 'v01';
	src: url("./resources/Game Of Squids.otf");
}
body{
	background-image: url("./resources/imgG01.jpg"),url("./resources/purpimg02.jpg");
 	background-blend-mode: lighten;
	background-repeat: no-repeat;
  	background-attachment: fixed;
	background-size: 100% 700px;
}
.gw {
  font-size: 105px;
  cursor: pointer;
  
  color: #fff;
  animation: glow 1s ease-in-out infinite alternate;
}

@-webkit-keyframes glow {
  from {
    text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #51087E, 0 0 40px #51087E, 0 0 50px #51087E, 0 0 60px #51087E, 0 0 70px #51087E;
  }
  
  to {
    text-shadow: 0 0 20px #fff, 0 0 30px #B24BF3, 0 0 40px #B24BF3, 0 0 50px #B24BF3, 0 0 60px #B24BF3, 0 0 70px #B24BF3, 0 0 80px #B24BF3;
  }
}
.imgcol{
	width:900px;
	display: flex;
	overflow-x: scroll;
}
.imgcol div{
	width:100%;
	display: grid;
	grid-template-columns: auto auto auto;
	grid-gap: 20px;
	padding: 10px;
	flex: none;
}
.imgcol div img{
	width: 100%;
	transition: transform 0.5s;
}
.imgcol::-webkit-scrollbar{
	display: none;
}
.imgcol img{
	width: 100%;
	height: 250px;
}
.imgcol-lv1{
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 10% auto;
}
.imgcol div img:hover{
	cursor: pointer;
	transform: scale(1.1);
}
#title{
	font-family: 'v01',sans-serif;
	font-size: 2em;
	color: white;
}
</style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div align="center">
<p id="title">GAME - STORE</p>
</div>
<div class="imgcol-lv1">
	<p class="gw" id="btbk">< </p>
	<div class="imgcol">
	<div>
		<img src="./resources/idimg01.jpg">
		<img src="./resources/idimg02.jpg">
		<img src="./resources/idimg03.jpg">
	</div>
	<div>
		<img src="./resources/idimg04.jpg">
		<img src="./resources/idimg05.jpg">
		<img src="./resources/idimg06.jpg">
	</div>
	</div>
	<p class="gw" id="btnxt"> ></p>
</div>
<script>
	let sb = document.querySelector(".imgcol");
	let bkbt = document.getElementById("btbk");
	let nxtbt = document.getElementById("btnxt");
	nxtbt.addEventListener("click",() =>{
		sb.style.scrollBehavior = "smooth";
		sb.scrollLeft += 900;
	});
	bkbt.addEventListener("click",() =>{
		sb.style.scrollBehavior = "smooth";
		sb.scrollLeft -= 900;
	});

</script>
</body>
</html>