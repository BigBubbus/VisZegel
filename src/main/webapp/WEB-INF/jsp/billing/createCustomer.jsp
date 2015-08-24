<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


        <!-- Display Result of /model/customer.jsp -->
        <div id="customerResult">

        </div>
        <script type="text/javascript">
            function myFunction() {
                $('#clientName').val("shit");
            }
            // Lets get our Attribute here and pass on to another jsp submodule in /billing
            $(".klantInput").keyup(function () {

                $.ajax({
                    type: "GET",
                    data: "mode=createcustomer&id=10",
                    url: "/operation"
                }).done(function (data) {
                    $('#customerResult').html(data);
                    $('#result_addcustomer').html(data);
                    var message = '<c:out value="${customer.name}"/>';
                    alert(message);

                    //$('#clientName').val("<c:out value='${customer.name}'/>");


                });

            });
        </script>
