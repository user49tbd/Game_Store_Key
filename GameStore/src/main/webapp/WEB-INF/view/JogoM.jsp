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
table{
	text-align: center;
}
body{
	background-image: url("./resources/purpimg02.jpg");
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
#bg{
	padding: 25px;
	background-color: white;
	border-radius: 12px;
}
#bg input[type=submit]{
	margin-top:25px;
	width: 140px;
	height: 40px;
	background-image: linear-gradient(to right, blue, indigo, violet);
	color: white;
	border: none;
	border-radius: 20px;
}
.gc{
	border: none;
	color: white;
	font-size: 70px;
	background-image: linear-gradient(to right, blue, indigo, violet);
	border-radius: 20px;
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
</style>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>
<c:if test="${not empty lstgms}">
	<form action="JogoM" method="get">
		<div align="center">
			<table id="tab1">
				<c:forEach items="${lstgms}" var="v">
					<tr><td><input type="submit" id="btt" name="btt" value="${v}"></td></tr>
				</c:forEach>
			</table>
		</div>
	</form>
</c:if>
<c:if test="${empty lstgms}">
<script>
function addf() {
	var c = document.getElementById("sl").value;
	  document.getElementById("gen").value += c+"|";
}
function cl() {
	  document.getElementById("gen").value = "";
}
</script>
<div align="center">
	<p id="title">MANTER - JOGO</p>
</div>
<form action="JogoM" method="get">
<input type="submit" id="bk" name="bk" value="voltar" class="bkbt">
</form>
<form action="JogoM" method="post">
<div id="bg" align="center">
<table>
	<tr>
		<c:set var="vl"></c:set>
		<td><c:out value="${usr }"></c:out></td>
		<td><input type="text" name="titulo" id="titulo" placeholder="#Titulo - Jogo"
		value="${gameM.titulo}"></td>
		<td><input type="number" name="ano" id="ano" placeholder="#Ano - Jogo" step="1"
		value="${gameM.ano}"></td>
		<td>
		<select id="sl" name="sl" onchange="addf()" >
			<c:forEach items="${lstgen}" var="ls">
				<option value="${ls }"><c:out value="${ls }"></c:out></option>
			</c:forEach>
		</select>
		</td>
		<td><input type="text" name="gen" id="gen" placeholder="#Genero - Jogo" readonly
		value="${gameM.cgen}"></td>
		<td><button type="button" id="btc" name="btc" onclick="cl()">clear</button></td>
	</tr>
</table>
<textarea rows="5" cols="50" id="desc" name="desc">
<c:out value="${gameM.descricao}"></c:out>
</textarea>
<table>
	<tr>
		<td><input type="submit" id="bt" name="bt" value="Inserir"></td>
		<td><input type="submit" id="bt" name="bt" value="Deletar"></td>
		<td><input type="submit" id="bt" name="bt" value="Atualizar"></td>
		<td><input type="submit" id="bt" name="bt" value="Buscar"></td>
		<td><input type="submit" id="bt" name="bt" value="Listar"></td>
	</tr>
</table>
</div>
<br>
<div align="center">
	<c:if test="${not empty msgerr }">
		<c:out value="${msgerr }"></c:out>
	</c:if>
</div>
<br>
<div align="center">
	<c:if test="${not empty lstgam }">
		<table id="tbexb">
			<thead>
				<tr>
					<td>TITULO</td>
					<td>ANO</td>
					<td>DESCRICAO</td>
					<td>GENERO</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${lstgam }" var="gm">
					<tr>
						<td><c:out value="${gm.titulo}"></c:out></td>
						<td><c:out value="${gm.ano}"></c:out></td>
						<td><textarea id="desc" name="desc" rows="5" cols="20"><c:out value="${gm.descricao}"></c:out></textarea></td>
						<td><c:out value="${gm.cgen}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<br>
	<c:if test="${not empty gameM.titulo}">
		<input type="submit" id="bt" name="bt" value="Gerenciar Chaves" class="gc">
	</c:if>
</div>
</form>
</c:if>
</body>
</html>