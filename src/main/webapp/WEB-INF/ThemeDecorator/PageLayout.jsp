<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>BootApp</title>

<link href="/ProjectResources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<link href="/ProjectResources/css/sb-admin-2.min.css" rel="stylesheet">
<link href="/ProjectResources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
  
<sitemesh:write property="head" />
<style>
.BodyMenuScroll{
	max-height: 559px;
	overflow-y: scroll;
	overflow-x: hidden;
}
.scroll-to-top {
    position: fixed;
    right: 2rem;
    bottom: 2rem;
    display: none;
    width: 2.75rem;
    height: 2.75rem;
    text-align: center;
    color: #fff;
    background: rgba(90,92,105,.5);
    line-height: 46px;
}
</style>
</head>
<body>

	<div id="wrapper">
		<jsp:include page='/WEB-INF/ThemeDecorator/LeftMenu.jsp' />
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<jsp:include page='/WEB-INF/ThemeDecorator/Header.jsp' />
				<main id="page-wrapper5">
				<div class="container-fluid" style="padding-right: 0px;">
					<div class="BodyMenuScroll" id="page-top" onscroll="myFunction()">
						<sitemesh:write property="body" />
						<jsp:include page='/WEB-INF/ThemeDecorator/Footer.jsp' />
						 <a class="scroll-to-top rounded" href="#page-top" onclick="gotoTOP();" style="display: inline;" id="topbtn">
						    <i class="fas fa-angle-up"></i>
   					     </a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
  <script src="/ProjectResources/vendor/jquery/jquery.min.js"></script>
  <script src="/ProjectResources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="/ProjectResources/vendor/jquery-easing/jquery.easing.min.js"></script>
  <script src="/ProjectResources/js/sb-admin-2.min.js"></script>
  <script src="/ProjectResources/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="/ProjectResources/vendor/datatables/dataTables.bootstrap4.min.js"></script>
  <script src="/ProjectResources/js/demo/datatables-demo.js"></script>
  <script src="/ProjectResources/vendor/chart.js/Chart.min.js"></script>
  <script src="/ProjectResources/js/demo/chart-area-demo.js"></script>
  <script src="/ProjectResources/js/demo/chart-pie-demo.js"></script>
  
  <script type="text/javascript">
/*   
	  window.onscroll=function(){
		if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20){
			document.getElementById("topbtn").style.display = "block";
		} else {
			document.getElementById("topbtn").style.display = "none";
		}
	  };
 */	
	  function gotoTOP(){
		  $("#page-top").animate({scrollTop: 0 }, "fast");
		  $("#topnavbar").height('4.375rem');
	  }
	  function myFunction(){
		  $("#topnavbar").height('40px');
	  }
	  
  </script>
  
</body>
</html>