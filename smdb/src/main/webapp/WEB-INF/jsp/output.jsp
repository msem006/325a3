<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<body>
		<h2>Here is the message from HelloSpringController.</h2>
		<p>
			${message}
		</p>
		<div>
			<c:forEach items="${movieList}" var="movie">
				{$movie._title}
			</c:forEach>
		</div>
	</body>
</html>