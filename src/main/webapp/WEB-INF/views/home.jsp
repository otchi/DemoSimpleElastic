<%@page import="com.edifixio.amine.utils.Duo"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Home</title>

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

	<form:form action="" method="post">
		<c:set var="bucketFacets" value="${facets}" scope="request"></c:set>

		<%
 			request.setAttribute("history", new LinkedList<Duo<String, String>>());
 			request.setAttribute("level", new Integer(0));
		%>



		<div>
			<%-- 				<form:input path="search"/> --%>
			<input type="submit" value="search">

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
		</div>


		<div><jsp:include page="facets.jsp"></jsp:include></div>


		<br>


	</form:form>



</body>
</html>
