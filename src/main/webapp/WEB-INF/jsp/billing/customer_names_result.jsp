<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<select>
<c:forEach items="${similar_customers}" var="customer">
    <option value="US"><c:out value="${customer.name}"/></option>
</c:forEach>
</select>