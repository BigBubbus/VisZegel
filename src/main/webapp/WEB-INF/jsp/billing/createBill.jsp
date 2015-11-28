<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<c:out value="${status}" />
<form:form id="add_customer_form" class="inclass" method="post"
	modelAttribute="billEntity" action="/viewBill" target="_blank">

	<table class="customer_table">
		<tr>
			<td><b>FillBill</b></td>
			<td><form:input id="fillBill" path="bill_id"></form:input><input
				id="billButtFill" type="submit" class="subbiButt"
				value="Rechnung füllen" /></td>

		</tr>
		<tr>
			<td><b>Datum:</b></td>
			<td><form:input id="billDate" path="date"></form:input></td>

		</tr>
		<tr>
			<td><b>Bezahlart:</b></td>
			<td><form:select path="payment_method">
					<form:option value="" label="-------- Select --------" />
					<form:option value="Bezahlt" label="Bezahlt" />
					<form:option value="Bankeinzug" label="Bankeinzug" />
					<form:option value="innerhalb von 7 Tagen"
						label="innerhalb von 7 Tagen" />
					<form:option value="innerhalb von 14 Tagen"
						label="innerhalb von 14 Tagen" />
				</form:select>
		</tr>

	</table>
	<!-- 
        --------------------------------------------------------------------------
        Customer Start
        --------------------------------------------------------------------------
        -->
	<div class="seperator30"></div>
	<table class="customer_table">
		<tr>
			<td>Kunden ID :</td>
			<td><form:input id="cusIdChange" class="kundenInput"
					path="cus_bill.id"></form:input></td>
		</tr>
		<tr>
			<td>Kunden Name :</td>
			<td><form:input id="cusNameChange" class="kundenInput"
					path="cus_bill.name"></form:input></td>
		</tr>
	</table>
	<div>
		<input id="billButtCus" type="submit" class="subbiButt" name="edit"
			value="Kunde füllen" />
	</div>

	<table class="customer_table">
		<thead>
			<tr>
				<td>Adresse</td>
				<td>Extra Regeln</td>
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
				<td><form:input class="add_input" path="cus_bill.adress"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.extra_rules"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.postcode"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.location"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.country"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.email"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.website"></form:input></td>
				<td><form:input class="add_input" path="cus_bill.btw_number"></form:input></td>
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
	<input id="billButt" type="submit" class="subbiButt" name="edit"
		value="Produkt hinzufügen" />
		F
	<input id="billButtFillProd" type="submit" name="edit"
		value="Produkte füllen" />
	<table class="customer_table">
		<thead>
			<tr>
				<td>Datum</td>
				<td>Product ID</td>
				<td>Produktbeschreibung</td>
				<td>Anzahl</td>
				<td>Preis</td>
				<td>BTWKategorie</td>
				<td>Liefertext</td>
				<td>Gewicht</td>
				<td>Löschen</td>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${billEntity.shopping_items}" var="allnames"
				varStatus="pStatus">
				<form:hidden path="shopping_items[${pStatus.index}].id"></form:hidden>
				<form:hidden path="shopping_items[${pStatus.index}].product.id"></form:hidden>
				<tr>

					<td><form:input class="shopping_datum"
							path="shopping_items[${pStatus.index}].datum"></form:input>
					<td><form:input name="${pStatus.index}"
							path="shopping_items[${pStatus.index}].product.product_id"
							class="productId"></form:input></td>
					<td><form:input class="shopping_item_name"
							name="item_${pStatus.index}"
							path="shopping_items[${pStatus.index}].product.description"></form:input>
					</td>
					<td><form:input
							path="shopping_items[${pStatus.index}].product.amount"></form:input></td>
					<td><form:input
							path="shopping_items[${pStatus.index}].product.price"></form:input></td>

					<td><form:input
							path="shopping_items[${pStatus.index}].product.btwCategory"></form:input></td>

					<td><form:input class="add_input"
							path="shopping_items[${pStatus.index}].delivery_text"></form:input>
					</td>
					<td><form:input class="add_input"
							path="shopping_items[${pStatus.index}].product.unitType"></form:input>
					</td>
					<td><input type="submit" class="removeShoppingItem"
						id="${pStatus.index}" name="delete" value="Löschen" /></td>


				</tr>
			</c:forEach>

		</tbody>
	</table>
	<!-- 
    --------------------------------------------------------------------------
    Products End
    --------------------------------------------------------------------------
    -->








	<input type="submit" name="edit" class="menuPoint"
		value="Rechnung Erstellen" />
</form:form>
<div id="customer_add_result"></div>
<div id="customer_name_result"></div>
<script type="text/javascript">
	$('#billDate').datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$('.shopping_datum').datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$("#billButt").click(function(event) {
		event.preventDefault();
		submitForm("&addShoppingItem=true&saveBill=false");
	});
	$("#billButtCus").click(function(event) {
		event.preventDefault();
		var str = $("#add_customer_form").serialize();
		$.ajax({
			type : "POST",
			data : str,
			url : "/fill_customer"
		}).done(function(data) {
			$("#mainContent").html(data);

		});
	});
	$("#billButtFill").click(function(event) {
		event.preventDefault();
		var str = $("#add_customer_form").serialize();
		$.ajax({
			type : "POST",
			data : str,
			url : "/fill_bill"
		}).done(function(data) {
			$("#mainContent").html(data);

		});
	});
	$("#billButtFillProd").click(function(event) {
		event.preventDefault();
		var str = $("#add_customer_form").serialize();
		$.ajax({
			type : "POST",
			data : str,
			url : "/fill_bill_products"
		}).done(function(data) {
			$("#mainContent").html(data);

		});
	});
	$(".productId,.shopping_item_name").keypress(function(e) {
		focused = this;
		if (e.keyCode == 13) {
			var str = $("#add_customer_form").serialize();
			$.ajax({
				type : "POST",
				data : str,
				url : "/fill_bill_products"
			}).done(function(data) {
				$("#mainContent").html(data);
				$(".shopping_item_name").focus();
				

			});
		}
	});
	$(".kundenInput").keypress(function(e) {
		if (e.keyCode == 13) {
			var str = $("#add_customer_form").serialize();
			$.ajax({
				type : "POST",
				data : str,
				url : "/fill_customer"
			}).done(function(data) {
				$("#mainContent").html(data);

			});
		}
	});
	$(".removeShoppingItem").click(
			function(event) {
				event.preventDefault();
				var str = $("#add_customer_form").serialize()
						+ "&shoppingItemId=" + this.id;
				$.ajax({
					type : "POST",
					data : str,
					url : "/delete_bill_product"
				}).done(function(data) {
					$("#mainContent").html(data);

				});
			});
	function fillCustomer() {
		submitForm();
	}
	function submitForm(addparam) {
		var str = $("#add_customer_form").serialize() + addparam;
		$.ajax({
			type : "POST",
			data : str,
			url : "/createbill_result"
		}).done(function(data) {
			$("#mainContent").html(data);

		});
	}
	// Lets get our Attribute here and pass on to another jsp submodule in /billing
	$("#button").click(function() {
		alert("BUTTON CLICKED");
		submitForm();
	});

	// ------------------------------------
	// On Customer Name Change 
	// ------------------------------------
	$("#cusNameChange").keyup(function(event) {
		var str = "" + $('#cusNameChange').val();
		$.ajax({
			type : "POST",
			data : "cusName=" + str,
			url : "/customer_name_change"
		}).done(function(data) {
			var cusNameArr = [];
			var obj = data;
			for (var i = 0; i < obj.length; i++) {
				cusNameArr.push(obj[i])
			}
			$("#cusNameChange").autocomplete({
				delay : 0,
				source : cusNameArr
			});
		});
	});
	// ------------------------------------
	// On Product Description Change 
	// ------------------------------------
	$(".shopping_item_name").keyup(function(event) {
		var str = "" + $(this).val();
		$.ajax({
			type : "POST",
			data : "shoppingItemName=" + str,
			url : "/product_description_change"
		}).done(function(data) {
			var productArr = [];
			var obj = data;
			for (var i = 0; i < obj.length; i++) {
				productArr.push(obj[i])
			}
			$(".shopping_item_name").autocomplete({
				delay : 0,
				source : productArr
			});
		});
	});

	/*
	Functionalities for adding shopping items or new Dates
	 */
	$(document).unbind('keyup').bind('keyup', function(e) {
		e.preventDefault();
		if (e.keyCode == 113) {
			var str = $("#add_customer_form").serialize();
			$.ajax({
				type : "POST",
				data : str,
				url : "/createbill_result"
			}).done(function(data) {
				$("#mainContent").html(data);
				$(".productId").focus();
				$(".productId").select();
			});
		}
	});

	/* ------------ Main Routine ------------------*/
	$(document).ready(function() {
	});
</script>
