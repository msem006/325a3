<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
	<title>325 a1</title>
	<link rel="icon" href="favicon.ico"> 
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/styles.css" />' />
	<link href='http://fonts.googleapis.com/css?family=Average+Sans' rel='stylesheet' type='text/css'>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>    
	<script src="http://www.red-team-design.com/wp-content/uploads/2011/02/modernizr-1.6.min_.js"></script>
	<script>    
	$(document).ready(function() {           
		if (!Modernizr.input.placeholder)
		{

			var placeholderText = $('#search').attr('placeholder');

			$('#search').attr('value',placeholderText);
			$('#search').addClass('placeholder');

			$('#search').focus(function() {				
				if( ($('#search').val() == placeholderText) )
				{
					$('#search').attr('value','');
					$('#search').removeClass('placeholder');
				}
			});

			$('#search').blur(function() {				
				if ( ($('#search').val() == placeholderText) || (($('#search').val() == '')) )                      
				{	
					$('#search').addClass('placeholder');					  
					$('#search').attr('value',placeholderText);
				}
			});
		}                
	});         
	</script>
	<title>smdb - We have all 50 shades</title>
</head>
<body>
	<!-- Top user login/info area -->
	<c:if test="${not empty user}" >
		<div>
			Welcome <c:out value="${user}"/>. | <a href='/admin'>Admin Area</a> | <a href='/logout'>Logout</a>
		</div>
	</c:if>
	<c:if test="${empty user}" >
		<div>
			<a href='/login'>Login</a>
		</div>
	</c:if>

	<!-- Site Content -->
	<h1> SMDB </h1>
	<div>
		<form:form method="post" action="searchResults" id="searchbox"commandname="searchQuery" modelAttribute="searchQuery">
		<form:input path="query" id="search" type="text" placeholder="Type here" />
		<input type="submit" id="submit" value="Search"/>
	</form:form>
</div>
<h2> Actor Info </h2>

<div id="name"> <c:out value="${actor.getFirstName()}"/> <c:out value="${actor.getLastName()}"/> </div>
<div id="dob"> <c:out value="${actor.getYearBorn()}"/> </div>

<h3>Filmography</h3>

<div id="movie">
	<c:forEach items="${movieList}" var="movie">
	<a href='movie?title=<c:out value="${movie.getTitle()}"/>&year=<c:out value="${movie.getProductionYear()}"/>'><c:out value="${movie.getTitle()}"/></a><br />
</c:forEach>
</div>

</body>
</html>
