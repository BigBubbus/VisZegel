
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<form:form class="inclass" id="add_product_form" method="post" modelAttribute="productEntity" action="/create_customer_result">  
    <table id="customer_table" style="width: 442px;">
        <thead>
            <tr>
                <td><b>Feldname</b></td>
                <td><b>Wert</b></td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><form:label path="product_id">Produkt ID:</form:label></td>  
                <td><form:input path="product_id"></form:input></td>  

                </tr>
                <tr>
                    <td><form:label path="description">Beschreibung:</form:label></td>  
                <td><form:input path="description"></form:input></td>  

                </tr>
                <tr>
                    <td><form:label path="price">Preis:</form:label></td>  
                <td><form:input path="price"></form:input></td>  

                </tr>
                <tr>
                    <td><form:label path="btwCategory">BTWKategorie:</form:label></td>  
                <td><form:input path="btwCategory"></form:input></td> 

                </tr> 
            </tbody>
        </table>
</form:form>


<div id="button" class="menuPoint">CLICK ME ON READY</div>
<div id="product_add_result"></div>

<script type="text/javascript">
    // Lets get our Attribute here and pass on to another jsp submodule in /billing


    $("#button").click(function (event) {
        event.preventDefault();
        var str = $("#add_product_form").serialize();
        $.ajax({
            type: "POST",
            data: str,
            url: "/create_product_result"
        }).done(function (data) {
            alert("Success!");
            $("#mainContent").html(data);
        });
    });
</script>