<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Home</title>
</head>
<body>

	<c:out value="${results}"></c:out>
	<br>
	<br>
	<c:out value="${facets}"></c:out>
	<br>
	<br>
	<c:forEach items="${facets}" var="facetEntry">
		<ul>
			<c:out value="${facetEntry.key}"></c:out>
			
			<c:set var="facet" value="${facetEntry.value}"></c:set>

			<c:forEach items="${facet.buckets}" var="bucketEntry">
				<li>
					<c:out value="${bucketEntry.key}"></c:out>
					<ul>
					<c:out value="${bucketEntry.value}"></c:out>
					<c:set var="bucket" value="${bucketEntry.value}"></c:set>
						<li><c:out value="${bucket.count}"></c:out></li>
 						<li><c:out value="${bucket.isChecked}"></c:out></li>
 						<c:set var="bucketAgg" value="${bucket.aggregations}"></c:set> 
<%--  						<c:set var="bucketFacet" value="${bucketAgg.aggregations}"></c:set>  --%>
 						<c:out value="${bucketAgg}"></c:out>
					</ul>
				</li>
			
			</c:forEach>

		</ul>

	</c:forEach>
</body>
</html>
