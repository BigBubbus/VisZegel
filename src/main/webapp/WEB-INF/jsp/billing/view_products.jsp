<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@page import="de.fischzegel.viszegel.model.Product"%>
<%@page import="java.util.List"%>



<table class="customer_table">
    <thead>
        <tr>
            <td>ID</td>
            <td>Beschreibung</td> 
            <td>Preis</td>  
            <td>btwKategorie</td> 
            <td>Edit</td> 
            <td>Delete</td> 
        </tr>
    </thead>
</table>
<%
    List<Product> prods = ((List<Product>) request.getAttribute("products"));
    for (int i = 0; i < prods.size(); i++) {
        Product cust = prods.get(i);
        request.setAttribute("temp_prod", cust);
%>

<form:form method="post" class="inclass" modelAttribute="temp_prod" action="/change_delete_result">  
    <table class="customer_table">
        <tbody>
            <tr>
                <td><form:input path="product_id"></form:input></td> 
                <td><form:input path="description"></form:input></td>  

                    <td><form:input path="price"></form:input></td>  



                    <td><form:input path="btwCategory"></form:input></td>  
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
            url: "/change_delete_result_product"
        }).done(function (data) {
            alert("Success!");
            $("#mainContent").html(data);
        });
    });

</script>

