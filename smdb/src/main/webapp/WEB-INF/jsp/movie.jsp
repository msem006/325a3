<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>smdb - We have all 50 shades</title>
</head>
<body>

<div>
	<c:out value="${movie.getTitle()}"/><br />
	<c:out value="${movie.getCountry()}"/><br />
	<c:out value="${movie.getMajorGenre()}"/><br />
	<c:out value="${movie.getRunTime()}"/><br />
	<c:out value="${movie.getProductionYear()}"/><br />
</div>

<div>
<c:forEach items="${personList}" var="person">

	<a href='actor?id=<c:out value="${person.getId()}"/>'><c:out value="${person.getFirstName()}"/> <c:out value="${person.getLastName()}"/></a><br />

</c:forEach>
</div>

</body>
</html>