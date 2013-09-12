<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>smdb - We have all 50 shades</title>
</head>
<body>
<h2>Search</h2>

<div>
<form:form method="post" action="searchResults">
<form:input path="query" /></td> 
<input type="submit" value="Go"/>
</form:form>
</div>

</body>
</html>
