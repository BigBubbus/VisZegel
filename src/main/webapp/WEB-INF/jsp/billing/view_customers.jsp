<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@page import="java.util.List"%>

<% for (int i = 0; i < 5 ; i++){
    Customer cust = ((List<Customer>)request.getAttribute("customers")).get(i);
    cust.setId(100);
    request.setAttribute("babo", cust);


%>

<form:form id="add_customer_form" method="post" modelAttribute="babo" action="/create_customer">  
    <table id="customer_table" style="width: 442px;">
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
        <tbody>

            <tr>
                <td><form:input path="id"></form:input></td>  
                <td><form:input path="name"></form:input></td>  
                <td><form:input path="adress"></form:input></td>  
                <td><form:input path="extra_rules"></form:input></td>  
                <td><form:input path="house_number"></form:input></td>   
                <td><form:input path="postcode"></form:input></td>   
                <td><form:input path="location"></form:input></td>  
                <td><form:input path="country"></form:input></td>   
                <td><form:input path="email"></form:input></td>   
                <td><form:input path="website"></form:input></td>   
                <td><form:input path="btw_number"></form:input></td>  
                    <td> <input type="submit" value="Edit"/></td>
                </tr>

            </tbody>
        </table>
</form:form>
<% } %>
<div id="button">CLICK ME ON READY</div>
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

