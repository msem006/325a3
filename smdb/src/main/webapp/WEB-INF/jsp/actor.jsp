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
	<h1><a href="index">SMDB</a></h1>
	<div>
		<form:form method="post" action="searchResults" id="searchbox"commandname="searchQuery" modelAttribute="searchQuery">
		<form:input path="query" id="search" type="text" placeholder="Type here" />
		<input type="submit" id="submit" value="Search"/>
	</form:form>
</div>
<h2> Actor Info </h2>

<div id="name"> <c:out value="${actor.getFirst_name()}"/> <c:out value="${actor.getLast_name()}"/> </div>
<div id="dob"> <c:out value="${actor.getYear_born()}"/> </div>

<h3>Filmography</h3>

<div id="movie">
	<c:forEach items="${roleList}" var="role">
		<a href='movie?title=<c:out value="${role.getTitle()}"/>&year=<c:out value="${role.getProduction_year()}"/>'><c:out value="${role.getTitle()}"/></a> as <c:out value="${role.getDescription()}"/><br />
	</c:forEach>
	<c:if test="${not empty user}" >
		<br>
		<a href='addRole?id=<c:out value="${actor.getId()}"/>'>Add another role</a>
	</c:if>
</div>

</body>
</html>
