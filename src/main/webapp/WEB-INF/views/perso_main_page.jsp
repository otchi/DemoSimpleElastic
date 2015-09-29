<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
table {
	border-collapse: collapse;
}

div {
	vertical-align: top;
	display: inline-block;
}

td {
	border-style: solid;
}
</style>
<title>Personalité</title>
</head>
<body>

	<table>

		<thead>
			<tr>
				<td>nom</td>
				<td>Œuvres majeur</td>
				<td>nationalité</td>
				<td>detail</td>
			</tr>
		</thead>
		<tbody>
		<c:set var="i" value="0"></c:set>
			<c:forEach items="${results}" var="r">
				<c:set var="v" value="${r.sourceObject}"></c:set>
				<tr>
					<td>${v.nom}</td>
					<td>${v.livreMajeur}</td>
					<td>${v.pays}</td>
					<td><a href="${context}/amine/complexData/${i}">detail</a></td>
					<c:set var="i" value="${i+1}"></c:set>
				</tr>
			</c:forEach>



		</tbody>


	</table>
</body>
</html>