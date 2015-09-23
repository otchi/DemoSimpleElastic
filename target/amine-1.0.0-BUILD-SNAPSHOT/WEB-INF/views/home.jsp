<%@page import="com.edifixio.amine.utils.Duo"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Home</title>

<style type="text/css">
	#facets{
		
		display: inline-block;
	}	
</style>

</head>
<body>

	<form:form   action="" method="post">
		<c:set var="bucketFacets" value="${facets}" scope="request"></c:set>

		<%
 			request.setAttribute("history", new LinkedList<Duo<String, String>>());
 			request.setAttribute("level", new Integer(0));
		%>
		

		
		<div id="facets">
			<jsp:include page="facets.jsp"></jsp:include>
		</div>
		
		<input type="submit" value="send">
	</form:form>



</body>
</html>
