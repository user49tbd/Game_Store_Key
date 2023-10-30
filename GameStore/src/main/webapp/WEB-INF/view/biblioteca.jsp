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
#tle{
	font-family: 'v01',sans-serif;
	font-size: 2em;
	color: white;
}
body{
	color: white;
	background-image: url("./resources/imgG02.jpg");
 	background-blend-mode: darken;
	background-repeat: no-repeat;
  	background-attachment: fixed;
	background-size: 100% 700px;
}
#bg{
	padding: 25px;
	background-color: white;
	border-radius: 12px;
}
#bg input{
	width: 100%;
}
#bg input[type=submit]{
	width: 140px;
	height: 40px;
	background-image: linear-gradient(to right, blue, indigo, violet);
	color: white;
	border: none;
	border-radius: 20px;
	
}
input[type=text]{
	background: none;
	border: none;
	border-bottom: 2px solid gray;
	width: 100%;
	height: 35px;
}
#tbexb{
  color: white;
  table-layout: fixed ;
  width: 50% ;
  height: 100% ;
  border-right-width: 20px;
  background-color: rgba(0,0,149, 0.4);
  border-left-width: 20px;
  border-block: 1px solid white;
  background-clip:content-box;
}
table{
	text-align: center;
}
</style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<form action="biblioteca" method="post">
<div align="center">
<p id="tle">Biblioteca</p>
	<table id="bg">
		<tr>
			<td colspan="2"><input type="text" id="title" name="title" placeholder="#titulo"></td>
		</tr>
		<tr>
			<td><input type="submit" id="bt" name="bt" value="Buscar"></td>
			<td><input type="submit" id="bt" name="bt" value="Listar"></td>
		</tr>
	</table>
	<br>
	<c:if test="${not empty lstJ }">
		<table id="tbexb">
		<thead>
			<tr>
				<td>Titulo</td>
				<td>Ano</td>
				<td>Genero(s)</td>
				<td>Chave</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${lstJ}" var="vl">
			<tr>
				<td><c:out value="${vl.titulo}"></c:out></td>
				<td><c:out value="${vl.ano}"></c:out></td>
				<td><c:out value="${vl.gen}"></c:out></td>
				<td><c:out value="${vl.key}"></c:out></td>
			</tr>
		</c:forEach>
		</tbody>
		</table>
	</c:if>
</div>
</form>
</body>
</html>