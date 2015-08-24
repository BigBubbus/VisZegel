<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<table id="customer_table" style="width: 442px;">
	<thead>
		<tr>
			<td>Feldname</td>
			<td>Wert</td>
		</tr>
	</thead>
	<tr>
		<td>Name</td>
		<td><input type="text" id="customer_name" class="customer_input"
			name="fname"></td>
	</tr>
	<tr>
		<td>Adresse</td>
		<td><input id="customer_address" type="text"
			class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>Extra Regeln</td>
		<td><input id="customer_extra_rules" type="text"
			class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>Hausnummer</td>
		<td><input id="customer_house_number" type="text"
			class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>Postcode</td>
		<td style="width: 157px;"><input id="customer_postcode"
			type="text" class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>Location</td>
		<td><input id="customer_location" type="text"
			class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>Land</td>
		<td><input id="customer_country" type="text"
			class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>Email</td>
		<td><input id="customer_email" type="text" class="customer_input"
			name="fname"></td>
	</tr>
	<tr>
		<td>Website</td>
		<td><input id="customer_website" type="text"
			class="customer_input" name="fname"></td>
	</tr>
	<tr>
		<td>BTW_Nummer</td>
		<td><input id="customer_btw" type="text" class="customer_input"
			name="fname"></td>
	</tr>
</table>
<div id="button" data-role="button">Click on button</div>
<div id="customer_add_result"></div>
<script type="text/javascript">
	// Lets get our Attribute here and pass on to another jsp submodule in /billing
	$("#button").click(function() {
		var get_result = "";
		var first = false;
		$("#customer_table input[type=text]").each(function() {
			if (!isNaN(this.value)) {
				if (first)
					get_result += "&";
				get_result += this.id;
				get_result += "=" + this.value;
				first = true;
			}
		});
		alert(get_result);
		$.ajax({
			type : "GET",
			data : get_result,
			url : "/create_customer"
		}).done(function(data) {
			$("#customer_add_result").html(data);

		});

	});
</script>

