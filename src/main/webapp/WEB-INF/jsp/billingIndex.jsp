<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

    </head>

    <body>
        <div id="status">
            <table class="customer_table">
                <tr>
                    <td><b>Status<b></td>
                                <td><b>Nachricht<b></td>
                                            </tr>
                                            <tr>
                                                <td><c:out value="${status.status}"/></td>
                                                <td><c:out value="${status.reason}"/></td>
                                            </tr>

                                            </table> 
                                            </div>
                                            <div id="heading">
                                               <!-- <img height="100px" src="<c:url value='/resources/images/viszegel.png' />"/>-->
                                            </div>
                                            <!--  This is where all modules will paste their results due to our ajax calls! -->
                                            <div id="menuPointWrapper">
                                                <div id="createCustomer" class="menuPoint" style="cursor: pointer;">Klant
                                                    toevogen</div>
                                                <div id="viewCustomers" class="menuPoint" style="cursor: pointer;">Klanten bekijken</div>
                                                <div id="createProduct" class="menuPoint" style="cursor: pointer;">Product toevoegen</div>
                                                <div id="viewProducts" class="menuPoint" style="cursor: pointer;">Producten bekijken</div>
                                                <div id="createBill" class="menuPoint" style="cursor: pointer;">Rekening
                                                    herstellen</div>


                                            </div>



                                            <div id="mainContent">
                                                Bitte einen Menüeintrag auswählen
                                            </div>

                                            <script>
                                                // Lets get our Attribute here and pass on to another jsp submodule in /billing


                                                $(".menuPoint").on('click', function () {

                                                    $.ajax({
                                                        type: "GET",
                                                        data: "mode=" + $(this).attr("id"),
                                                        url: "/operation"
                                                    }).done(function (data) {


                                                        $("#mainContent").html(data);

                                                    });
                                                });
                                            </script>
                                            </body>
                                            </html>
