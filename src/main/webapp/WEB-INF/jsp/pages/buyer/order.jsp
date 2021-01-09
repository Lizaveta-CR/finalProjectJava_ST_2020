<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 09.01.2021
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <title>Order</title>
</head>
<body>
<c:if test="${not empty order}">
    <c:forEach items="${order.productIts.iterator()}" var="product">
        <div class="product-preview-container">
            <td>${product.model}</td>
            <td>${product.price}</td>
                <%--        <td><a href="/removeFilmFromOrder/${film.name}"><spring:message code="lbl.delete"/></a></td>--%>
        </div>
    </c:forEach>
</c:if>
<div class="card-footer">
    <form action="<c:url value="/products/list"/>" method="get">
        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                key="label.button.back"/></button>
    </form>
</div>
</body>
</html>
