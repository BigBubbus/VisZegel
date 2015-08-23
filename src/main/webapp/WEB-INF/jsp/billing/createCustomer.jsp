<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to Spring Web MVC project</title>
</head>

<body>
	<table style="width: 442px;">
		<thead>
			<tr>
				<td>Feldname</td>
				<td>Wert</td>
			</tr>
		</thead>
		<tr>
			<td>klantid</td>
			<td><input type="text" class="klantInput" name="fname"
				value="<c:out value='${customer.name}'/>"></td>
		</tr>
		<tr>
			<td>Name</td>
			<td><input type="text" class="klantInput" name="fname"></td>
		</tr>
		<tr>
			<td>Result</td>
			<td><input id="clientName" type="text" class="klantInput"
				name="fname"></td>
		</tr>
<button onclick="myFunction()">Click me</button>
	</table>
	<div id="result_addcustomer">
		<c:out value='${customer.name}' />
	</div>
	<script type="text/javascript">
	function myFunction(){
	$('#clientName').val("shit");
	}
		// Lets get our Attribute here and pass on to another jsp submodule in /billing
		$(".klantInput").keyup(function() {

			$.ajax({
				type : "GET",
				data : "mode=createcustomer&id=10",
				url : "/operation"
			}).done(function(data) {
				$('#result_addcustomer').html(data);
				$('#result_addcustomer').html(data);
				var message = '<c:out value="${customer.name}"/>';
				alert(message);

				//$('#clientName').val("<c:out value='${customer.name}'/>");
				

			});

		});
	</script>
</body>
</html>
