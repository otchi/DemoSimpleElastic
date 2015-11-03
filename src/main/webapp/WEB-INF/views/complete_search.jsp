<%@page import="com.edifixio.simplElastic.utils.Duo" %>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>


<head>

<title>simple search</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="http://code.jquery.com/jquery-1.11.3.min.js"
	type="text/javascript"></script>



<!-- ****************** css ********************************************************************* -->
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

<!-- ************************ java Script********************************************************************** -->






</head>

<body>

	<c:set var="bucketFacets" value="${facets}" scope="request"></c:set>

	<%
		request.setAttribute("history", new LinkedList<Duo<String, String>>());
		request.setAttribute("level", new Integer(0));
	%>

	<form:form method="post" action="">
		<div>
			<form:label path="search"> search : </form:label>
			<form:input path="search" />
			<input type="submit" value="send" name="send" /> <br>
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
			<br> <input type="submit" value="last" name="last" /> <br>
			<input type="submit" value="next" name="next" /> <br>
			<c:out value="${pagination}"></c:out>
			
			<input type="hidden"    id="nbr_element" value="${results.size()}"/>
			<table>
				<thead>
					<tr>
						<td>voiture</td>
						<td>cylendres</td>
						<td>annee</td>
						<td>pays</td>
						<td>poid</td>
					</tr>
				</thead>
				<tbody>
					<c:set var="i" value="0"></c:set>
					<c:forEach items="${results}" var="r">

						<c:set var="v" value="${r.sourceObject}"></c:set>
						<tr>
							<td><c:out value="${v.nomVoiture}"></c:out></td>
							<td><c:out value="${v.cylendres}"></c:out></td>
							<td><c:out value="${v.annee}"></c:out></td>
							<td><c:out value="${v.pays}"></c:out></td>
							<td id="poid${i}"><c:out value="${i}"></c:out></td>
						</tr>
						<c:set var="i" value="${i+1}"></c:set>
					</c:forEach>
				</tbody>

			</table>
		</div>

		<div><jsp:include page="facets.jsp"></jsp:include></div>

	</form:form>


<script type="text/javascript">

	var i;
	var n = $('#nbr_element').attr("value");
// 	alert(n);

	for (i = 0; i < n; i++) {
		$("#poid" + i).bind('click', function(event) {
			var target = $(event.target);
// 			alert(target.text());
			doAjaxPost(parseInt(target.text()));
		});
	}

	function doAjaxPost(index) {
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/amine/completeSearch/ajaxCall",
			data : "index=" + index ,
			success : function(response) {
				alert(response);
				$("#poid" + index).text(response);
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}
</script>


</body>


</html>