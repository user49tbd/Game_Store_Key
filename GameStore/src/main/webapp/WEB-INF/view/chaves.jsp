<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
table{
	text-align: center;
}
@font-face{
	font-family: 'v01';
	src: url("./resources/Game Of Squids.otf");
}
#title{
	font-family: 'v01',sans-serif;
	font-size: 2em;
	color: white;
}
body{
	color: white;
	background-image: url("./resources/purpimg03.jpg");
 	background-blend-mode: darken;
	background-repeat: no-repeat;
  	background-attachment: fixed;
	background-size: 100% 700px;
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
#bg{
	color: black;
	padding: 25px;
	background-color: white;
	border-radius: 12px;
}
input[type=number]{
	background: none;
	border: none;
	border-bottom: 2px solid gray;
	width: 100%;
	height: 35px;
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
    url("./resources/purpimg02.jpg") padding-box fixed,
    linear-gradient(to right,blue,violet, purple) border-box;
}
.bkbt{
	position: absolute;
	boder: none;
	color: red;
	top: 55px;
	color:#fff;
  margin: auto;
  border:1px solid transparent;
  border-radius:50px;
  text-align:center;
  font-size:15px;
  background:
    linear-gradient(to right,blue,violet, purple) border-box;
}
</style>
</head>
<body>
<form action="chaves" method="post">
<input type="submit" id="bt" name="bt" value="voltar" class="bkbt">
<div align="center">
	<p id="title">MANTER - CHAVES</p>
	<table id="bg">
		<tr>
			<td><c:out value="${gameM.titulo}"></c:out></td>
			<td><c:out value="${gameM.ano}"></c:out></td>
			<td><input type="number" name="qtd" id="qtd" placeholder="#Quantidade - Chaves" step="1"></td>
		</tr>
	</table>
	<br>
	<table id="tab1">
	<tr>
		<td><input type="submit" id="bt" name="bt" value="Inserir"></td>
		<td><input type="submit" id="bt" name="bt" value="Deletar"></td>
		<td><input type="submit" id="bt" name="bt" value="Buscar"></td>
		<td><input type="submit" id="bt" name="bt" value="Listar"></td>
	</tr>
</table>
<br>
<c:if test="${not empty msgerr }">
		<c:out value="${msgerr }"></c:out>
</c:if>
</div>
</form>
<br>
<div align="center">
	<c:if test="${not empty lstK }">
		<table id="tbexb">
			<thead>
				<tr>
					<th>TITULO</th>
					<th>ANO</th>
					<th>CHAVE</th>
					<th>STATUS</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${lstK }" var="k">
					<tr>
						<td><c:out value="${k.titulo }"></c:out></td>
						<td><c:out value="${k.ano }"></c:out></td>
						<td><c:out value="${k.chave }"></c:out></td>
						<td><c:out value="${k.status }"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
</body>
</html>