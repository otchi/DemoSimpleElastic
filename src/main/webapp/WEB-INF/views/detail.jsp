<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personalité</title>
</head>
<body>
<p>prix : <c:out value="${detail.prix}"></c:out></p> 
<p>biographie : <c:out value="${detail.biographie}"></c:out></p> 

</body>
</html>