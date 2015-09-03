<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 

<form:form class="inclass" id="add_customer_form" method="post" modelAttribute="customer-entity" action="/create_customer_result">  
    <table id="customer_table" style="width: 442px;">
        <thead>
            <tr>
                <td>Feldname</td>
                <td>Wert</td>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><form:label path="name">Name:</form:label></td>  
                <td><form:input path="name"></form:input></td>  

                </tr>
                <tr>
                    <td><form:label path="adress">Adresse</form:label></td>  
                <td><form:input path="adress"></form:input></td>  
                </tr>

                <tr>
                    <td><form:label path="extra_rules">Extra Regeln</form:label></td>  
                <td><form:input path="extra_rules"></form:input></td>  
                </tr>

                <tr>
                    <td><form:label path="house_number">Hausnummer</form:label></td>  
                <td><form:input path="house_number"></form:input></td>  

                </tr>
                <tr>
                    <td><form:label path="postcode">Postcode</form:label></td>  
                <td><form:input path="postcode"></form:input></td>  
                </tr>
                <tr>
                    <td><form:label path="location">Location</form:label></td>  
                <td><form:input path="location"></form:input></td>  
                </tr>
                <tr>
                    <td><form:label path="country">Land</form:label></td>  
                <td><form:input path="country"></form:input></td>  
                </tr>
                <tr>
                    <td><form:label path="email">Email</form:label></td>  
                <td><form:input path="email"></form:input></td>  
                </tr>
                <tr>
                    <td><form:label path="website">Website</form:label></td>  
                <td><form:input path="website"></form:input></td>  
                </tr>
                <tr>
                    <td><form:label path="btw_number">BTW_Nummer</form:label></td>  
                <td><form:input path="btw_number"></form:input></td>  
                </tr>
                <tr>
                    <td><form:label path="btw_number_final">BTW_Nummer_Final</form:label></td>  
                <td><form:input path="btw_number_final"></form:input></td>  
                </tr>
               
            </tbody>
        </table>
</form:form>
<div id="button" class="menuPoint">CLICK ME ON READY</div>
<div id="customer_add_result"></div>
<script type="text/javascript">
    // Lets get our Attribute here and pass on to another jsp submodule in /billing

    
       $("#button").click(function (event) {
        event.preventDefault();
        var str = $("#add_customer_form").serialize();
        $.ajax({
            type: "POST",
            data: str,
            url: "/create_customer_result"
        }).done(function (data) {
            alert("Success!");
            $("#mainContent").html(data);
        });
    });
</script>

