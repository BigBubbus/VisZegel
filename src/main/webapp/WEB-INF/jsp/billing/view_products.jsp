<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@page import="java.util.List"%>



<table class="customer_table">
    <thead>
        <tr>
            <td>ID</td> 
            <td>Name:</td>  
            <td>Adresse</td> 
            <td>Extra Regeln</td> 
            <td>Hausnummer</td> 
            <td>Postcode</td> 
            <td>Location</td> 
            <td>Land</td> 
            <td>Email</td>  
            <td>Website</td> 
            <td>BTW_Nummer</td>  
            <td>Edit</td>
            <td>Delete</td>
        </tr>
    </thead>
</table>
<%
    List<Customer> custs = ((List<Customer>) request.getAttribute("customers"));
    for (int i = 0; i < custs.size(); i++) {
        Customer cust = custs.get(i);
        request.setAttribute("temp_cust", cust);
%>

<form:form method="post" class="inclass" modelAttribute="temp_cust" action="/change_delete_result">  
    <table class="customer_table">
        <tbody>
            <tr>

                <td><form:input class ="add_input" path="id"></form:input></td>  
                <td><form:input class ="add_input" path="name"></form:input></td>  
                <td><form:input class ="add_input" path="adress"></form:input></td>  
                <td><form:input class ="add_input" path="extra_rules"></form:input></td>  
                <td><form:input class ="add_input" path="house_number"></form:input></td>   
                <td><form:input class ="add_input" path="postcode"></form:input></td>   
                <td><form:input class ="add_input" path="location"></form:input></td>  
                <td><form:input class ="add_input" path="country"></form:input></td>   
                <td><form:input class ="add_input" path="email"></form:input></td>   
                <td><form:input class ="add_input" path="website"></form:input></td>   
                <td><form:input class ="add_input" path="btw_number"></form:input></td>  
                    <td><input type="submit" class="subbiButt" name="edit" value="Edit" /></td>
                    <td><input type="submit" class="subbiButt" name="delete" value="Delete" /></td>
                </tr>

            </tbody>
        </table>
</form:form>
<% }%>



<div id="customer_add_result"></div>

<script type="text/javascript">

    var show_mode = "";
    $(".subbiButt").click(function (event) {
        show_mode = $(this).attr('name');
    });

    $(".inclass").submit(function (event) {

        event.preventDefault();
        var str = $(this).serialize() + "&mode=" + show_mode;

        $.ajax({
            type: "POST",
            data: str,
            url: "/change_delete_result"
        }).done(function (data) {
            alert("Success!");
            $("#mainContent").html(data);
        });
    });

</script>

