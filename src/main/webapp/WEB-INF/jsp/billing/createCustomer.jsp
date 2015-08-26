<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 

<form:form method="post" modelAttribute="customer-entity" action="/create_customer_result">  
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
            <td colspan="2">  
                <input value="Submit" type="submit">  
            </td>   
        </tr>  
        <tr>
            <td>Extra Regeln</td>
            <td><input id="extra_rules" type="text"
                       class="customer_input" name="fname"></td>
        </tr>
        <tr>
            <td>Hausnummer</td>
            <td><input id="house_number" type="text"
                       class="customer_input" name="fname"></td>
        </tr>
        <tr>
            <td>Postcode</td>
            <td style="width: 157px;"><input id="postcode"
                                             type="text" class="customer_input" name="fname"></td>
        </tr>
        <tr>
            <td>Location</td>
            <td><input id="location" type="text"
                       class="customer_input" name="fname"></td>
        </tr>
        <tr>
            <td>Land</td>
            <td><input id="country" type="text"
                       class="customer_input" name="fname"></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input id="email" type="text" class="customer_input"
                       name="fname"></td>
        </tr>
        <tr>
            <td>Website</td>
            <td><input id="website" type="text"
                       class="customer_input" name="fname"></td>
        </tr>
        <tr>
            <td>BTW_Nummer</td>
            <td><input id="btw_number" type="text" class="customer_input"
                       name="fname"></td>
        </tr>
        </tbody>
    </table>
</form:form>
<div id="button" data-role="button">Click on button</div>
<div id="customer_add_result"></div>
<script type="text/javascript">
    // Lets get our Attribute here and pass on to another jsp submodule in /billing
    $("#button").click(function () {
        var get_result = "";
        var first = false;
        $("#customer_table input[type=text]").each(function () {
            if (!isNaN(this.value)) {
                if (first)
                    get_result += "&";
                get_result += "customer." + this.id;
                get_result += "=" + this.value;
                first = true;
            }
        });
        alert(get_result);
        $.ajax({
            type: "GET",
            data: get_result,
            url: "/create_customer"
        }).done(function (data) {
            $("#customer_add_result").html(data);

        });

    });
</script>

