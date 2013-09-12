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
<div>
	<c:out value="${actor.getFirstName()}"/><br />
	<c:out value="${actor.getLastName()}"/><br />
	<c:out value="${actor.getYearBorn()}"/><br />
</div>

<div>
	<c:forEach items="${movieList}" var="movie">
		<a href='movie?title=<c:out value="${movie.getTitle()}"/>&year=<c:out value="${movie.getProductionYear()}"/>'><c:out value="${movie.getTitle()}"/></a><br />
	</c:forEach>
</div>

</body>
</html>
