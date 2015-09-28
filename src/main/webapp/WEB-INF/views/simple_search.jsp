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
</head>

<body>

	<form:form method="post" action="">
		<form:label path="search"> search : </form:label>
		<form:input path="search" />
		<input type="submit" value="send" name="send" />
		<br>
		<form:label path="size"> page size : </form:label>
		<form:select path="size">
			<form:option value="1"></form:option>
			<form:option value="2"></form:option>
			<form:option value="5"></form:option>
			<form:option value="10"></form:option>
			<form:option value="15"></form:option>
			<form:option value="20"></form:option>
			<form:option value="30"></form:option>
			<form:option value="50"></form:option>
			<form:option value="100"></form:option>
			<form:option value="200"></form:option>
		</form:select>
		<br>
		<input type="submit" value="last" name="last" />
		<br>
		<input type="submit" value="next" name="next" />
		<br>
		<c:out value="${pagination}"></c:out>

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