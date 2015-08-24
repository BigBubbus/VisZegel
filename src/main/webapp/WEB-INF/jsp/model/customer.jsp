
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="de.fischzegel.viszegel.model.Customer"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--
private int id;
private String name;
private String extra_rules;
private String adress;
private int house_number;
private int postcode;
private String location;
private String country;
private String email;
private String website;
private int btw_number;
private int btw_number_final;-->
<table style="width: 442px;">
    <thead>
        <tr>
            <td>Feldname</td>
            <td>Wert</td>
        </tr>
    </thead>
    <tr>
        <td>klantid</td>
        <td><input type="text" class="klantInput" name="fname"></td>
    </tr>
    <tr>
        <td>Name</td>
        <td><input type="text" class="klantInput" name="fname"
                   value="<c:out value='${customer.name}'/>"></td>
    </tr>
    <tr>
        <td>Address</td>
        <td><input id="clientName" type="text" class="klantInput"
                   name="fname"></td>
    </tr>
    <tr>
        <td>Address</td>
        <td><input id="clientName" type="text" class="klantInput"
                   name="fname"></td>
    </tr>
        <tr>
        <td>Address</td>
        <td><input id="clientName" type="text" class="klantInput"
                   name="fname"></td>
    </tr>
</table>

