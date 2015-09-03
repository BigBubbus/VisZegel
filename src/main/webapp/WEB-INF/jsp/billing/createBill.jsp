<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form:form  id="add_customer_form" method="post" modelAttribute="billEntity" action="/createbill_result">  

    <table class="customer_table">
        <tr>
            <td><b>Datum:</b></td>
            <td><form:input path="date"></form:input></td>

            </tr>
            <tr>
                <td><b>Bezahlart:</b></td>
                <td><form:select path="payment_method">
                    <form:options/>
                </form:select></td>
        </tr>

    </table>
    <!-- 
    --------------------------------------------------------------------------
    Customer Start
    --------------------------------------------------------------------------
    -->
    <div class="seperator30"></div>
    <div>
        Kunden ID : 
        <form:input id="cusIdChange" class ="add_input" path="cus_bill.id"></form:input><input type="submit" class="billButtCus" value="Kunde f�llen" />
        </div>
        <table class="customer_table">
            <thead>
                <tr>
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
                </tr>
            </thead>
            <tbody>
                <tr>             
                    <td><form:input class ="add_input" path="cus_bill.name"></form:input></td>  
                <td><form:input class ="add_input" path="cus_bill.adress"></form:input></td>  
                <td><form:input class ="add_input" path="cus_bill.extra_rules"></form:input></td>  
                <td><form:input class ="add_input" path="cus_bill.house_number"></form:input></td>   
                <td><form:input class ="add_input" path="cus_bill.postcode"></form:input></td>   
                <td><form:input class ="add_input" path="cus_bill.location"></form:input></td>  
                <td><form:input class ="add_input" path="cus_bill.country"></form:input></td>   
                <td><form:input class ="add_input" path="cus_bill.email"></form:input></td>   
                <td><form:input class ="add_input" path="cus_bill.website"></form:input></td>   
                <td><form:input class ="add_input" path="cus_bill.btw_number"></form:input></td>  
                </tr>

            </tbody>
        </table>
        <!-- 
        --------------------------------------------------------------------------
        Customer End
        --------------------------------------------------------------------------
        -->
        <!-- 
        --------------------------------------------------------------------------
        Products Start
        --------------------------------------------------------------------------
        -->
        <div class="seperator30"></div>
        <input type="submit" class="billButt" value="Produkt hinzuf�gen" />
        <table class="customer_table">
            <thead>
                <tr>
                    <td>Liefertext</td> 
                    <td>Produktbeschreibung</td>
                </tr>
            </thead>
            <tbody>

            <c:forEach items="${billEntity.shopping_items}" var="allnames" varStatus="pStatus">  
                <tr>
                    <td>   <form:input class ="add_input" path="shopping_items[${pStatus.index}].delivery_text"></form:input>       </td>         
                    <td>   <form:input class ="add_input" path="shopping_items[${pStatus.index}].product.description"></form:input>    </td> 

                    </tr>

            </c:forEach>

        </tbody>
    </table>
    <!-- 
    --------------------------------------------------------------------------
    Products End
    --------------------------------------------------------------------------
    -->









</form:form>
<div id="button" class="menuPoint">CLICK ME ON READY</div>
<div id="customer_add_result"></div>
<script type="text/javascript">
    $(".billButt").click(function (event) {
        event.preventDefault();
        submitForm("&addShoppingItem=true");
    });
    $(".billButtCus").click(function (event) {
        event.preventDefault();
        submitForm("&addShoppingItem=false");
    });
    function fillCustomer() {
        submitForm();
    }
    function submitForm(addparam) {
        var str = $("#add_customer_form").serialize() + addparam;
        $.ajax({
            type: "POST",
            data: str,
            url: "/createbill_result"
        }).done(function (data) {
            $("#mainContent").html(data);

        });
    }
    // Lets get our Attribute here and pass on to another jsp submodule in /billing
    $("#button").click(function () {
        alert("BUTTON CLICKED");
        submitForm();
    });
</script>
