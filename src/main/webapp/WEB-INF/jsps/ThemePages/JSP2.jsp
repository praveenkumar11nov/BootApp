<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>SB Admin 2 - Tables</title>
<sitemesh:write property="head" />
</head>
<body id="page-top">
	<div id="wrapper">
		<jsp:include page='/WEB-INF/ThemeDecorator/LeftMenu.jsp' />
		<jsp:include page='/WEB-INF/ThemeDecorator/Resources.jsp' />
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<jsp:include page='/WEB-INF/ThemeDecorator/Header.jsp' />
				<main id="page-wrapper5">
				<div class="container-fluid">
					<sitemesh:write property="body" />
				</div>
			</div>
			<jsp:include page='/WEB-INF/ThemeDecorator/Footer.jsp' />
		</div>
	</div>
</body>
</html>