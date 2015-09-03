<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form:form id="add_customer_form" method="post" modelAttribute="billEntity" action="/createbill_result">  

    <div>
        <b>Date:</b>
        <form:input path="date"></form:input>
        </div>
        <div>
            <b>Bezahlart:</b>
        <form:select path="payment_method">
            <form:options/>
        </form:select>
    </div>
    <!-- 
    --------------------------------------------------------------------------
    Customer Start
    --------------------------------------------------------------------------
    -->


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
            </tr>
        </thead>
        <tbody>
            <tr>

                <td><form:input class ="add_input" onchange="javascript:fillCustomer();" path="cus_bill.id"></form:input></td>  
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
    <c:out value="${billEntity.date} " ></c:out> 

        <table class="customer_table">
            <thead>
                <tr>
                    <td>ID</td> 
                </tr>
            </thead>
            <tbody>

            <c:forEach items="${billEntity.shopping_items}" var="allnames" varStatus="pStatus">  
                <tr>
                    <td>   <form:input class ="add_input" path="shopping_items[${pStatus.index}].delivery_text"></form:input>               
                    <td><input type="submit" class="billButt" value="Produkt hinzufügen" /></td>

                </tr>

            </c:forEach>

        </tbody>
    </table>









</form:form>
<div id="button" class="menuPoint">CLICK ME ON READY</div>
<div id="customer_add_result"></div>
<script type="text/javascript">

    $(".billButt").click(function (event) {
        event.preventDefault();
        addShoppingItem();
    });
    function addShoppingItem() {
        submitForm("&addShoppingItem=true");
    }
    function fillCustomer() {
        submitForm();
    }
    function submitForm(addparam) {
        var str = $("#add_customer_form").serialize()+addparam;

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

        submitForm();
    });
</script>
