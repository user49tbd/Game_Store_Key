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
#title{
	font-family: 'v01',sans-serif;
	font-size: 2em;
	color: white;
}
input[type=text],{
	background: none;
	border: none;
	border-bottom: 2px solid gray;
	width: 100%;
	height: 35px;
}
body{
	background-image: url("./resources/imgG04.jpg");
 	background-blend-mode: darken;
	background-repeat: no-repeat;
  	background-attachment: fixed;
	background-size: 100% 700px;
}
#tab1 { 
  width: 50%;
  table-layout: fixed;
  border-collapse: collapse;
}
#tab1 input[type=submit] {
  
  width:100%;
  height:50px;
  cursor:pointer;
  color:#fff;
  margin: auto;
  border:3px solid transparent;
  border-radius:50px;
  text-align:center;
  font-size:15px;
  background:
    url("./resources/purpimg03.jpg") padding-box fixed,
    linear-gradient(to right,blue,violet, purple) border-box;
}
input[type=text]{
	background: none;
	border: none;
	border-bottom: 2px solid gray;
	width: 100%;
	height: 35px;
}
table{
	text-align: center;
}
#bg{
	padding: 25px;
	background-color: white;
	border-radius: 12px;
}
#bg input[type=submit]{
	width: 140px;
	height: 40px;
	background-image: linear-gradient(to right, blue, indigo, violet);
	color: white;
	border: none;
	border-radius: 20px;
}
</style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div align="center">
<p id="title">BUSCAR - JOGO</p>
</div>
<form action="buscarJ" method="post">
<div align="center">
<table id="bg">
<tr>
<td><input type="text" id="nomeJogo" name= "nomeJogo" placeholder="#Nome do Jogo" required></td>
<td><input type="submit" id="bt" name="bt" value="BUSCAR"></td>
</tr>
</table>
</div>
</form>
<br>
<form action="gameS" method="get">
<div align="center">
<c:if test="${not empty Tlst}">
	<table id="tab1">
		<c:forEach items="${Tlst}" var="v">
			<tr><td><input type="submit" id="btt" name="btt" value="${v}"></td></tr>
		</c:forEach>
	</table>
</c:if>
</div>
</form>
</body>
</html>