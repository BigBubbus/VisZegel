<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Please Login to the Zegel Enterprise</title>
        <style>
            #loginBox {	vertical-align: middle; margin:0 auto;
             width:200px;
             height:200px;}
            h1 { color: green; }

        </style>
    </head>

    <body>
        <c:out value="${username}"/>
        <div id="loginBox" style="">
            <h2>Signup Details</h2>
            <form action="/login" method="GET">
                <br/>Username:<input type="text" name="username">
                <br/>Password:<input type="password" name="password">
                <br/><input type="submit" value="Submit">
            </form>
        </div>
    </body>
</html>
