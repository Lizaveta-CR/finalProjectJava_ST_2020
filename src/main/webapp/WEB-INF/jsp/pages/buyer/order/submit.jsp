<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 09.01.2021
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="f" uri="function" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Submit order</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/jQuery/jquery-3.5.1.min.js"></script>
</head>
<c:choose>
    <c:when test="${not empty order}">
        <c:set var="buyer" value="${order.buyer}"/>
        <c:set var="address" value="${order.buyer.address}"/>
    </c:when>
</c:choose>
<body>
<c:if test="${not empty order}">
    <c:if test="${not empty message}">
        <c:forEach items="${message}" var="item" varStatus="status">
            <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
            <form action="<c:url value="/buyer/address"/>" method="get">
                <button class="btn btn-primary btn-lg btn-block" type="submit"><fmt:message
                        key="label.buyer.address"/></button>
            </form>
            <form action="<c:url value="/buyer/edit"/>" method="get">
                <button class="btn btn-primary btn-lg btn-block" type="submit"><fmt:message
                        key="label.register.telephone"/></button>
            </form>
        </c:forEach>
    </c:if>
    <div class="container">
        <div class="py-5 text-center">
            <img class="d-block mx-auto mb-4" src='<c:url value="/img/order.jpg"/>'
                 alt="" width="72" height="72">
            <h2><fmt:message key="label.order.submit.title"/></h2>
        </div>
        <div class="row">
            <div class="col-md-4 order-md-2 mb-4">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-muted"><fmt:message key="label.order.submit.cart"/></span>
                    <c:set var="size" value="${fn:length(order.productIts)}"/>
                    <span class="badge badge-secondary badge-pill">${size}</span>
                </h4>
                <ul class="list-group">
                    <c:forEach items="${order.productIts.iterator()}" var="product">
                        <li class="list-group-item d-flex justify-content-between lh-condensed">
                            <div>
                                <h6 class="my-0">${product.model}</h6>
                            </div>
                            <span class="text-muted">${product.price}x${orderItem[product]}</span>
                        </li>
                    </c:forEach>
                    <li class="list-group-item d-flex justify-content-between">
                        <span><fmt:message key="label.order.total"/></span>
                        <strong>${order.price}</strong>
                        <c:set var="buyerBonus" value="${buyer.bonus}"/>
                        <c:choose>
                            <c:when test="${f:isBigDecimalZero(buyerBonus)}">
                            </c:when>
                            <c:otherwise>
                                <span><fmt:message key="label.buyer.bonus.total"/></span>
                                <strong class="my-0">${buyerBonus}</strong>
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="bonus" id="bonus"
                                           value="${buyerBonus}">
                                    <label class="custom-control-label" for="bonus"><fmt:message
                                            key="label.buyer.bonus"/></label>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
            <form action="<c:url value="/buyer/order/submit"/>" method="post">
                <div class="col-md-8 order-md-1">
                    <div class="row">
                        <label for="telephone"><fmt:message key="label.register.telephone"/></label>
                        <div class="mb-3">
                            <div class="col-md-4 mb-3">
                                <input class="form-control" type="text" id="telephone" name="telephone"
                                       value="${buyer.telephone}" required="">
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="city"><fmt:message key="label.register.city"/></label>
                                <input class="form-control" type="text" id="city" name="city" value="${address.city}"
                                       required="">
                                <label for="street"><fmt:message key="label.register.street"/></label>
                                <input class="form-control" type="text" id="street" name="street"
                                       value="${address.street}" required="">
                                <label for="apartmentNumber"><fmt:message key="label.register.apartNumber"/></label>
                                <input class="form-control" type="text" id="apartmentNumber" name="apartmentNumber"
                                       value="${address.apartmentNumber}" required="">
                                <label for="houseNumber"><fmt:message key="label.register.house_number"/></label>
                                <input class="form-control" type="text" id="houseNumber" name="houseNumber"
                                       value="${address.houseNumber}" required="">
                                <label for="zip_code"><fmt:message key="label.register.zip_code"/></label>
                                <input class="form-control" type="text" id="zip_code" name="zip_code"
                                       value="${address.zipCode}" required="">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <hr class="mb-4">
                        <button class="btn btn-primary btn-lg btn-block" type="submit"><fmt:message
                                key="label.confirm"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</c:if>
<div class="col-xs-12 col-sm-4 emphasis">
    <form action="<c:url value="/welcome"/>" method="get">
        <button class="btn btn-info btn-block" type="submit"><fmt:message
                key="label.button.back"/></button>
    </form>
</div>
</body>
</html>
