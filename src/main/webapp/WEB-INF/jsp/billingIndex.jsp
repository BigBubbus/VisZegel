<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style type="text/css">
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BillingIndex</title>
<link href="<c:url value='/resources/css/billing.css' />"
	rel="stylesheet" type="text/css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>

<body>
	<div id="heading">
		<img src="<c:url value='/resources/images/fischzegel.jpg' />" />
	</div>
	<!--  This is where all modules will paste their results due to our ajax calls! -->
	<div id="menuPointWrapper">
		<div id="createCustomer" class="menuPoint" style="cursor: pointer;">Klanten
			toevogen</div>
		<div id="checkBill" class="menuPoint" style="cursor: pointer;">Rekenining
			bekijken</div>
		<div id="test" class="menuPoint" style="cursor: pointer;">Dummy</div>
	</div>
	<br>
	<div id="mainContent">
	</div>
	<script>
		// Lets get our Attribute here and pass on to another jsp submodule in /billing
		$(".menuPoint").on('click', function() {

			$.ajax({
				type : "GET",
				data : "mode=" + $(this).attr("id"),
				url : "/operation"
			}).done(function(data) {
				$("#mainContent").html(data);

			});
		});
	</script>
</body>
</html>
