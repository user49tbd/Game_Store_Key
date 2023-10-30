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
	background-repeat: no-repeat;
  	background-attachment: fixed;
	background-size: 100% 700px;
}
input[type=email],input[type=password],input[type=text]{
	background: none;
	border: none;
	border-bottom: 2px solid gray;
	width: 100%;
	height: 35px;
}
select{
	height: 40px;
}
input:hover{
	background-color: rgba(255, 255,255, 0.4);
}
#bg{
	border-radius: 12px;
	width: 450px;
	padding: 25px;
	background-color: white;
}
#bg input[type=submit]{
	margin-top:25px;
	width: 100%;
	height: 40px;
	background-image: linear-gradient(to right, blue, indigo, violet);
	color: white;
	border: none;
	border-radius: 20px;
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
<jsp:include page="menu.jsp"></jsp:include>

<div align="center">
<p id="title">LOGIN</p>
</div>
<form action="logger" method="get">
<input type="submit" id="bt" name="bt" value="voltar" class="bkbt">
<div align="center">
	<table id="tab1">
		<tr>
			<td><input type="submit" id="bt" name="bt" value="Criar conta"></td>
			<td><input type="submit" id="bt" name="bt" value="Entrar"></td>
			<c:if test="${not empty usr}">
				<td><input type="submit" id="bt" name="bt" value="Desconectar"></td>
				<td><input type="submit" id="bt" name="bt" value="Manter Conta"></td>
			</c:if>
		</tr>
	</table>
</div>
</form>
<br>
<c:if test="${ not empty op}">
<form action="logger" method="post">
<div align="center">
<table id="bg">
		<tr>
		<c:if test="${op eq 'M' }">
			<td><c:out value="${usr}"></c:out></td>
			<td><c:out value="${tpusr}"></c:out></td>
		</c:if>
		<c:if test="${op ne 'M' }">
			<td><input type="text" id="ln" name="ln" placeholder="#Nome do usuario" required></td>
			<td>
			<select id="ltp" name="ltp">
				<option value="P" selected>P</option>
				<option value="J">J</option>
			</select>
			</td>
		</c:if>
		</tr>
		<c:if test="${op eq 'C' or op eq 'M'}">
			<tr>
				<td colspan="2">
				<input type="email" id="lm" name="lm" placeholder="#E-mail do usuario">
				</td>
			</tr>
		</c:if>
		<tr>
			<td colspan="2"><input type="password" id="ls" name="ls" minlength="8"
			placeholder="#Senha"></td>
		</tr>
		<tr>
		<c:if test="${op eq 'E' }">
			<td colspan="2"><input type="submit" id="bt" name="bt" value="Loggar"></td>
		</c:if>
		<c:if test="${op eq 'C' }">
			<td colspan="2"><input type="submit" id="bt" name="bt" value="Criar conta"></td>
		</c:if>
		</tr>
		<c:if test="${op eq 'M' }">
		<tr>
			<td colspan="2"><input type="submit" id="bt" name="bt" value="Atualizar"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" id="bt" name="bt" value="Excluir"></td>
		</tr>
	</c:if>
	</table>
<br>
</div>
</form>
</c:if>
<br>
<div align="center">
	<c:if test="${not empty emsg }">
	<c:out value="${emsg}"></c:out>
</c:if>
</div>
</body>
</html>