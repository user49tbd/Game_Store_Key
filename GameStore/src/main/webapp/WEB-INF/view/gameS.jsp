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
	background-image: url("./resources/imgG03.jpg"), url("./resources/purpimg02.jpg");
 	background-blend-mode: lighten;
	background-repeat: no-repeat;
  	background-attachment: fixed;
	background-size: 100% 700px;
}
#tab1 input[type=submit] {
  width:250px;
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
#tbexb textarea{
	background:none;
	border:none;
	color:white;
}
#tbexb textarea::-webkit-scrollbar {
	display: none;
    }
.parent {
  text-align: center;
}
.child {
  width: 250px;
  height: 150px;
  display: inline-block;
  vertical-align: middle;
  color: black;
  background-color: white;
  border-radius: 12px;
  padding: 20px;
}
.child img{
	width: 100%;
	height: 25%;
}
#tbs textarea{
	background:none;
	border:none;
	color:black;
	width: 100%;
}
#tbs textarea::-webkit-scrollbar {
	display: none;
    }
#bgimg{
	background-image: url("./resources/purpimg03.jpg");
 	background-blend-mode: darken;
	background-repeat: no-repeat;
  	background-attachment: fixed;
}
</style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<div align="center">
<p id="title">GAME</p>
</div>
<br>
<div align="center">
	<c:if test="${not empty game }">
		<div class='parent'>
  			<div class='child'>
  				<table id="tbs">
					<tr>
						<td><c:out value="${game.titulo}"></c:out></td>
						<td><c:out value="${game.ano}"></c:out></td>
					</tr>
					<tr>
						<td colspan="2">Publicadora: <c:out value="${game.publicadora}"></c:out></td>
					</tr>
					<tr>
						<td colspan="2"><textarea id="desc" name="desc" rows="5" cols="30"
						readonly>Descricao: <c:out value="${game.descricao}"></c:out></textarea></td>
					</tr>
				</table>
  			</div>
  			<div class='child' id="bgimg">
  			</div>
		</div>
	</c:if>
</div>
<br>
<form action="gameS" method="post">
	<div align="center">
		<table id="tab1">
		<tr>
			<td><input type="submit" id="res" name="res" value="Resgate"></td>
			<td><input type="submit" id="res" name="res" value="Avaliar"></td>
			<td><input type="submit" id="res" name="res" value="Listar Avaliacoes"></td>
		</tr>
		</table>
		<c:if test="${not empty msgerr }">
			<c:out value="${msgerr }"></c:out>
		</c:if>
	</div>
</form>
<c:if test="${not empty lsav }">
	<div align="center">
		<p>Media: <c:out value="${med }"></c:out></p>
		<br>
		<table id="tbexb">
			<thead>
				<tr>
					<th>USUARIO</th>
					<th>NOTA</th>
					<th>DESCRICAO</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${lsav }" var="av">
					<tr>
						<td><c:out value="${av.usr}"></c:out></td>
						<td><c:out value="${av.nota}"></c:out></td>
						<td><textarea id="desc" name="desc" rows="5" cols="20"><c:out value="${av.descricao}"></c:out></textarea></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>
</body>
</html>