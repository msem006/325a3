<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>smdb - We have all 50 shades</title>
</head>
<body>

	<div>
	<form:form method="post" action="searchResults">
	<form:input path="query" /></td> 
	<input type="submit" value="Go"/>
	</form:form>
	</div>
<h2>Search Results</h2>



<c:forEach items="${movieList}" var="movie">
	<a href='movie?title=<c:out value="${movie.getTitle()}"/>&year=<c:out value="${movie.getProductionYear()}"/>'><c:out value="${movie.getTitle()}"/></a><br />
</c:forEach>

<c:forEach items="${personList}" var="person">
	<a href='actor?id=<c:out value="${person.getId()}"/>'><c:out value="${person.getFirstName()}"/> <c:out value="${person.getLastName()}"/></a><br />
</c:forEach>

</body>
</html>
