<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
	
	<link rel="icon" href="favicon.ico"> 
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/styles.css" />' />
	<link href='http://fonts.googleapis.com/css?family=Average+Sans' rel='stylesheet' type='text/css'>
	
	<script src="/resources/jquery.js"></script>    
    <script src="/resources/modernizr.js"></script>
	<script src="/resources/modern.js"></script>
	<title>SMDB - The comprehensive movie database</title>
</head>
<body>
	<!-- Top user login/info area -->
	<c:if test="${not empty user}" >
		<div>
			Welcome <c:out value="${user}"/> | <a href='/admin'>Admin Area</a> | <a href='/logout'>Logout</a>
		</div>
	</c:if>
	<c:if test="${empty user}" >
		<div>
			<a href='/login'>Login</a>
		</div>
	</c:if>

	<!-- Site Content -->
	<h1><a href="index">SMDB</h1>
	<div>
		<form:form method="post" action="searchResults" id="searchbox"commandname="searchQuery" modelAttribute="searchQuery">
		<form:input path="query" id="search" type="text" placeholder="Type here" /> 
		<input type="submit" id="submit" value="Search"/>
	</form:form>
</div>
<h2> Movie Info </h2>
<div id="country">
	Title: <c:out value="${movie.getTitle()}"/><br />
	Country: <c:out value="${movie.getCountry()}"/><br />
	Genre: <c:out value="${movie.getMajor_genre()}"/><br />
	Runtime: <c:out value="${movie.getRun_time()}"/><br />
	Year: <c:out value="${movie.getProduction_year()}"/><br />
</div>
<h3>Actors</h3>
<div id="actor">
	<c:forEach items="${personList}" var="person">

	<a href='actor?id=<c:out value="${person.getId()}"/>'><c:out value="${person.getFirst_name()}"/> <c:out value="${person.getLast_name()}"/></a><br />

</c:forEach>
</div>

</body>
</html>
