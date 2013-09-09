<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>smdb - We have all 50 shades</title>
</head>
<body>
<h2>Search</h2>
<form:form method="post" action="searchResults.html">

<!-- <td><form:label path="query"></form:label></td> -->
<form:input path="query" /></td> 

<form:select path="type">
  <form:option value="Movie" label="Movie" />
  <form:option value="Actor" label="Actor" />
</form:select>

<input type="submit" value="Go"/>	
	
</form:form>
</body>
</html>
