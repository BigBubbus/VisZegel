<form:form id="add_customer_form" method="post" modelAttribute="bill-entity" action="/create_customer">  
    
    
    
    
    <td><form:input path="adress"></form:input></td>  
    
    
    <table id="bill_table" style="width: 442px;">
        <thead>
        </thead>
        <tbody>
            </tbody>
        </table>
</form:form>
<div id="button" class="menuPoint">CLICK ME ON READY</div>
<div id="customer_add_result"></div>
<script type="text/javascript">
    // Lets get our Attribute here and pass on to another jsp submodule in /billing
    $("#button").click(function () {
        var str = $("#add_customer_form").serialize();
        $.ajax({
            type: "POST",
            data: str,
            url: "/createbill"
        }).done(function (data) {
            $("#customer_add_result").html(data);

        });

    });
</script>
