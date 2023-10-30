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
#tle1{
	font-family: 'v01',sans-serif;
	color: white;
}
body {
    margin: 0;
}
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
  position: fixed;
  top: 0;
  width: 100%;
}

li {
  float: left;
  color: white;
  text-align: center;
  padding: 14px 16px;
}

li a {
  display: block;
  color: white;
  text-decoration: none;
}

li a:hover:not(.active) {
  background-color: #111;
}

.active {
  background-color: #04AA6D;
}
</style>
</head>
<body>
<nav>
<ul>
<li id="tle1">GAME-STORE</li>
<c:if test="${not empty usr }">
<li><c:out value="${usr }"></c:out></li>
</c:if>
<li><a href="index">Index</a></li>
<li><a href="buscarJ">Games</a></li>
<li><a href="logger">Login</a></li>
<c:if test="${not empty usr and tpusr eq 'J' }">
<li><a href="biblioteca">Biblioteca</a></li>
</c:if>
<c:if test="${not empty usr and tpusr eq 'P' }">
<li><a href="JogoM">Gerenciar Jogos</a></li>
</c:if>
</ul>
</nav>
<br>
<br>
<br>
</body>
</html>