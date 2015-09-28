<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>simple search</title>

<style type="text/css">
div {
	vertical-align: top;
	display: inline-block;
}

td {
	border-style: solid;
}
</style>
</head>

<body>

	<form:form method="post" action="">
		<form:label path="search"> search : </form:label><form:input path="search"/>
		<input type="submit" value="send"/><br>
		<form:label path="size"> page size : </form:label> <form:input path="size"/>
	
	</form:form>
		<form:form method="post" action="${context}/amine/simpleSearch/last">
		<form:hidden path="search"/>
		<form:hidden path="size"/>
		<input type="submit" value="last"/><br>
	</form:form>
	
	<form:form method="post" action="${context}/amine/simpleSearch/next">
		<form:hidden path="search"/>
		<form:hidden path="size"/>
		<input type="submit" value="next"/><br>
	</form:form>
	


	<table>

		<thead>
			<tr>
				<td>voiture</td>
				<td>cylendres</td>
				<td>annee</td>
				<td>pays</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${results}" var="r">
				<c:set var="v" value="${r.sourceObject}"></c:set>
				<tr>
					<td>${v.nomVoiture}</td>
					<td>${v.cylendres}</td>
					<td>${v.annee}</td>
					<td>${v.pays}</td>
				</tr>
			</c:forEach>



		</tbody>


	</table>
</body>


</html>