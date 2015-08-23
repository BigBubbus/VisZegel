<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
        <input type="text" class="klantInput" name="fname"><br>
        <input type="text" name="fname"><br>
        <input type="text" name="fname"><br>
        <input type="text" name="fname"><br>
        <input type="text" name="fname"><br>
        
        	<script>
		// Lets get our Attribute here and pass on to another jsp submodule in /billing
		$(".klantInput").keyup(function() {
/*
			$.ajax({
				type : "GET",
				data : "mode=" + $(this).attr("id"),
				url : "/operation"
			}).done(function(data) {
				$("#mainContent").html(data);

			});
			*/
		});
	</script>
    </body>
</html>
