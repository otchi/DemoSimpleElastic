<%@page import="com.edifixio.amine.utils.Duo"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Home</title>
</head>
<body>
<form:form action="" method="">
	<c:set var="bucketFacets" value="${facets}"  scope="request"></c:set>
<%-- 	<%! @SuppressWarnings("unchecked") %> --%>
	<%  
		request.setAttribute("history", new LinkedList<Duo<String,String>>());
		request.setAttribute("level", new Integer(0));
	%>
<%-- 	<c:out value="${history}"></c:out> --%>
	<jsp:include page="facets.jsp"></jsp:include>
</form:form>
	


</body>
</html>
