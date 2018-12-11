<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BOOTAPP</title>
</head>
<body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//alert("Header.jsp");
		});
	</script>

	<table style="width: 101%;padding-right: 30px;color: brown;background-color: bisque;">
		<tr>
			<form action="/logout" method="post">
				<td align="right">
					<strong>Welcome <%=session.getAttribute("username")%></strong>&nbsp;
					<button type="submit" class="btn btn-danger" style="color: blue;background: bottom;">Sign Out</button> 
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</td>
			</form>
		</tr>
	</table>

</body>
</html>