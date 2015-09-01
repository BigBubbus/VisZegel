<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@page import="java.util.List"%>



<table id="customer_table">
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
        </tr>
    </thead>
    <%
        List<Customer> custs = ((List<Customer>) request.getAttribute("customers"));
        for (int i = 0; i < custs.size(); i++) {
            Customer cust = custs.get(i);
            request.setAttribute("temp_cust", cust);
    %>

    <form:form id="add_customer_form" method="post" modelAttribute="temp_cust" action="/create_customer">  
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
                    <td> <input type="submit" value="Edit"/></td>
                </tr>

            </tbody>
    </form:form>
    <% }%>
</table>


<div id="customer_add_result"></div>

<script type="text/javascript">
    // Lets get our Attribute here and pass on to another jsp submodule in /billing
    $("#button").click(function () {
        var str = $("#add_customer_form").serialize();
        $.ajax({
            type: "POST",
            data: str,
            url: "/create_customer_result"
        }).done(function (data) {
            $("#customer_add_result").html(data);

        });

    });
</script>

