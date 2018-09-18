<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Spring Security Example</title>
<link href="/bootstrap.min.css" rel="stylesheet">
<script src="/jquery-2.2.1.min.js"></script>
<script src="/bootstrap.min.js"></script>
</head>
<body>
	<div>
		<div class="container" style="margin: 50px; border: 1px solid green;">
			<div>
				<form action="/logout" method="post">
					<button type="submit" class="btn btn-danger">Log Out</button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</div>
			<div class="row text-center">
				<strong>Welcome User.......</strong>
			</div>

		</div>
	</div>
</body>
</html>