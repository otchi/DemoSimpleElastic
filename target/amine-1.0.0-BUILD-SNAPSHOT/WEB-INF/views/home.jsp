<%@page import="com.edifixio.amine.utils.Duo"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Home</title>

<style type="text/css">
	div {
	 vertical-align: top;
	 display:  inline-block;
	 
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
		


			
				<form:input path="search"/><input type="submit" value="search">
				<div><jsp:include page="facets.jsp"></jsp:include></div>
			


		
		
		<br>
		
		
	</form:form>



</body>
</html>
