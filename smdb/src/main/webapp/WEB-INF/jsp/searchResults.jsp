<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
	<title>SMDB - The comprehensive movie database</title>
	<link rel="icon" href="favicon.ico"> 
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/styles.css" />' />
	<link href='http://fonts.googleapis.com/css?family=Average+Sans' rel='stylesheet' type='text/css'>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>    
	<script src="http://www.red-team-design.com/wp-content/uploads/2011/02/modernizr-1.6.min_.js"></script>

	<script src="/resources/jquery.js"></script>    
    <script src="/resources/modernizr.js"></script>
	<script src="/resources/modern.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		    $("#login-link").click(function(){
		        $("#login-panel").slideToggle(200);
		    })
		})
		$(document).keydown(function(e) {
		    if (e.keyCode == 27) {
		        $("#login-panel").slideToggle(200);
		    }
		});
	</script>

</head>
<body>
	<!-- Top user login/info area -->
	<c:if test="${not empty user}" >
		<div>
			Welcome <c:out value="${user}"/> | <a href='admin'>Admin Area</a> | <a href='logout'>Logout</a>
		</div>
	</c:if>
	<c:if test="${empty user}" >
		<div id="demo-header">
			<a id="login-link" title="Login" href="#login">Login</a>
			<div id="login-panel">
				<form name='f' action="<c:url value='j_spring_security_check' />"
						method='POST'>
				<label>Username: <input type="text" name="j_username" value=""> </label>
				<label>Password: <input type="password" name="j_password" value=""> </label>
				<input type="submit" name="submit" value="Sign In"> <small>Press ESC to close</small></form>
				</div>
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
<h2>Search Results for "<c:out value="${query}" />"</h2>
<h3>Movies</h3>
<div id"sResult">
	<c:forEach items="${movieList}" var="movie">
	<a href='movie?title=<c:out value="${movie.getTitle()}"/>&year=<c:out value="${movie.getProduction_year()}"/>'><c:out value="${movie.getTitle()}"/></a><br />
</c:forEach></div>
<h3>Actors</h3>
<div id"sResult">
	<c:forEach items="${personList}" var="person">
	<a href='actor?id=<c:out value="${person.getId()}"/>'><c:out value="${person.getFirst_name()}"/> <c:out value="${person.getLast_name()}"/></a><br />
</c:forEach>
</div>


</body>
</html>
