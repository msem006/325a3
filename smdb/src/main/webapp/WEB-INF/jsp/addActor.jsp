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
			Welcome <c:out value="${user}"/> | <a href='admin'>Admin Area</a> | <a href='logout'>Logout</a>
		</div>
	</c:if>
	<c:if test="${empty user}" >
		<div>
			<a href='login'>Login</a>
		</div>
	</c:if>

	<!-- Site Content -->
	<h1><a href="index">SMDB</a></h1>
	<div>
		<form:form method="post" action="searchResults" id="searchbox" commandname="searchQuery" modelAttribute="searchQuery">
			<form:input path="query" id="search" type="text" placeholder="Type here" />
			<input type="submit" id="submit" value="Search"/>
		</form:form>
	</div>
	<c:if test="${not empty error}" >
		<div class="result error">
			<c:out value="${error}"/>
		</div>
	</c:if>
	<c:if test="${not empty person.getId()}" >
		<div class="result success">
			Actor successfully added. View the actor <a href='actor?id=<c:out value="${person.getId()}"/>'>here</a>
		</div>
	</c:if>
	<div>
		<form:form method="post" action="addActor" commandname="addActor" modelAttribute="addActor">

		<form:label path="first_name">First Name</form:label>
		<form:input path="first_name" type="text" />
		<form:errors path="first_name"/><br />

		<form:label path="last_name">Last Name</form:label>
		<form:input path="last_name" type="text" />
		<form:errors path="last_name"/><br />

		<form:label path="year_born">Year Born</form:label>
		<form:input path="year_born" type="text" />
		<form:errors path="year_born"/><br />

		<form:label path="title">Movie Title</form:label>
		<form:input path="title" type="text" />
		<form:errors path="title"/><br />

		<form:label path="production_year">Production Year</form:label>
		<form:input path="production_year" type="text" />
		<form:errors path="production_year"/><br />

		<form:label path="description">Role Description</form:label>
		<form:input path="description" type="text" />
		<form:errors path="description"/><br />

		<input type="submit" value="Add"/>
		</form:form>
	</div>
</body>
</html>