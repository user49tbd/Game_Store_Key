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
table{
	text-align: center;
}
.bkbt{
	position: absolute;
	boder: none;
	color: red;
	color:#fff;
  margin: auto;
  border:1px solid transparent;
  border-radius:50px;
  text-align:center;
  font-size:15px;
  background:
    linear-gradient(to right,blue,violet, purple) border-box;
}
#bg1{
	color: black;
	border-radius: 12px;
	width: 450px;
	padding: 25px;
	background-color: white;
}
#bg1 textarea{
	width: 100%;
}
#bg input[type=submit]{
	margin-top:25px;
	width: 125px;
	height: 40px;
	background-image: linear-gradient(to right, blue, indigo, violet);
	color: white;
	border: none;
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
<form action="avaliar" method="post">
<input type="submit" id="bt" name="bt" value="voltar" class="bkbt">
<div align="center">
<p id="tle">MANTER - AVALIACAO</p>
	<table id="bg1">
		<tr>
			<td><c:out value="${usr}"></c:out></td>
			<td><input type="text" id="title" name="title" placeholder="#titulo" 
			value="${avmS.titulo}"></td>
			<td><input type="number" id="ano" name="ano" placeholder="#ano" 
			value="${avmS.ano}"></td>
			<td><input type="number" id="nota" name="nota" placeholder="#nota"
			step="0.5" min="0" value="${avmS.nota}" max="10"></td>
		</tr>
		<tr>
		<td colspan="4">
			<textarea id="desc" name="desc" rows="5" cols="50"><c:out value="${avmS.descricao}"></c:out></textarea>
		</td>
		</tr>
	</table>
		<table id="bg">
			<tr>
			<td><input type="submit" id="bt" name="bt" value="Inserir"></td>
			<td><input type="submit" id="bt" name="bt" value="Deletar"></td>
			<td><input type="submit" id="bt" name="bt" value="Atualizar"></td>
			<td><input type="submit" id="bt" name="bt" value="Buscar"></td>
			<td><input type="submit" id="bt" name="bt" value="Listar"></td>
			</tr>
		</table>
</div>
</form>
<div align="center">
<c:if test="${not empty msg }">
	<c:out value="${msg }"></c:out>
</c:if>
</div>
<br>
<div align="center">
	<c:if test="${not empty lstav }">
		<table id="tbexb">
			<thead>
				<tr>
					<th>TITULO</th>
					<th>ANO</th>
					<th>NOTA</th>
					<th>DESCRICAO</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${lstav}" var="av">
					<tr>
						<td><c:out value="${av.titulo}"></c:out></td>
						<td><c:out value="${av.ano}"></c:out></td>
						<td><c:out value="${av.nota}"></c:out></td>
						<td><textarea id="desc" name="desc" rows="5" cols="20"><c:out value="${av.descricao}"></c:out></textarea></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>
</body>
</html>