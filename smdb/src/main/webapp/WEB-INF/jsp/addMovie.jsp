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
			<form:input path="query" id="search" type="text" placeHolder="Type here" />
			<input type="submit" id="submit" value="Search"/>
		</form:form>
	</div>
	<c:if test="${not empty error}" >
		<div class="result error">
			<c:out value="${error}"/>
		</div>
	</c:if>
	<c:if test="${empty error}" >
		<c:if test="${not empty movie.getTitle()}">
			<div class="result success">
				<a href='movie?title=<c:out value="${movie.getTitle()}"/>&year=<c:out value="${movie.getProduction_year()}"/>'><c:out value="${movie.getTitle()}"/></a> has been added successfully
			</div>
		</c:if>
	</c:if>
	<div>
		<form:form method="post" action="addMovie" commandname="addMovie" modelAttribute="addMovie">
		<form:label path="title">Title</form:label>
		<form:input path="title" type="text" />
		<form:errors path="title"/><br />

		<form:label path="production_year">Production Year</form:label>
		<form:input path="production_year" type="text" />
		<form:errors path="production_year"/><br />

		<form:label path="country">Country</form:label>
		<form:input path="country" type="text" />
		<form:errors path="country"/><br />

		<form:label path="major_genre">Major Genre</form:label>
		<form:input path="major_genre" type="text" />
		<form:errors path="major_genre"/><br />

		<form:label path="run_time">Run Time</form:label>
		<form:input path="run_time" type="text" />
		<form:errors path="run_time"/><br />

		<input type="submit" value="Add"/>
		</form:form>
	</div>
</body>
</html>