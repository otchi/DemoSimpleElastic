<%@page import="com.edifixio.simplElastic.utils.Duo" %>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:forEach items="${bucketFacets}" var="facetEntry">
	<%-- 	<c:if test="${ not empty facetEntry.key}"> --%>
	<ul>

		<li><c:out value="${facetEntry.key}"></c:out> <c:set
				var="facet" value="${facetEntry.value}"></c:set>
			<ul>
				<c:forEach items="${facet.buckets}" var="bucketEntry">

							

					<c:set var="bucket" value="${bucketEntry.value}"></c:set>

					<%-- 					<li><c:out value="${bucket.count}"></c:out></li> --%>
					<%-- 					<li><c:out value="${bucket.isChecked}"></c:out></li> --%>

					<c:set var="fKey" value="${facetEntry.key}"></c:set>
					<c:set var="bKey" value="${bucketEntry.key}"></c:set>
					<%!@SuppressWarnings("unchecked")%>
					<%
						String first = (String) pageContext.getAttribute("fKey");
								String second = (String) pageContext.getAttribute("bKey");
								Duo<String, String> duo = new Duo<String, String>(first, second);
								List<Duo<String, String>> history = ((List<Duo<String, String>>) request.getAttribute("history"));
								history.add(duo);
								request.setAttribute("history", history);
					%>


					<%-- 					<c:out value="---------------------->${history}"> </c:out> --%>

					<c:set var="i" value="true"></c:set>
					<c:set var="path" value=""></c:set>

					<c:forEach items="${history}" var="duo">
						<c:choose>
							<c:when test="${i}">
								<c:set var="path"
									value="${path}aro.facets[${duo.first}].buckets[${duo.seconde}]."></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="path"
									value="${path}facetableAggrs[${duo.first}].buckets[${duo.seconde}]."></c:set>
							</c:otherwise>


						</c:choose>
						<c:set var="i" value="false"></c:set>
					</c:forEach>

					<%-- 						<c:out value="+++++++++++++>>>>>${facets}"></c:out> --%>
					<%-- 						<c:out value="+++++++++++++>>>>>${path}"></c:out> --%>
					<%-- 						<form:checkbox path="facets['${facetEntry.key}'].buckets['${bucketEntry.key}'].isChecked"/> --%>
					<li>
						<c:out value="${bKey} :"></c:out> 
						<form:checkbox path="${path}isChecked" />
						<c:out value="(${bucket.count})"></c:out> 
					</li>


<%-- 					<c:set var="bucketAgg" value="${bucket.aggregations}"></c:set> --%>
					<c:set var="bucketFacets" value="${bucket.facetableAggrs}"
						scope="request"></c:set>
					<c:if test="${bucketFacets.size() > 0  }">

						<%
							Integer index = (Integer) request.getAttribute("level");
										request.setAttribute("level", ((int) index + 1));
						%>
						<jsp:include page="facets.jsp"></jsp:include>
						
						<%
 						 	index = (Integer) request.getAttribute("level");
 							request.setAttribute("level", ((int) index - 1));
 						%> 
					</c:if>




					<%
						history.remove((int) ((Integer) request.getAttribute("level")));
					%>


				</c:forEach>

			</ul> <%-- 		<c:out value="${level}"></c:out> --%>
		</li>
	</ul>


</c:forEach>

