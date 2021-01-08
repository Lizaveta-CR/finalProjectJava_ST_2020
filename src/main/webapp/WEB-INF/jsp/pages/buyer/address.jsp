<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 05.01.2021
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Address</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/jQuery/jquery-3.5.1.min.js"></script>
</head
<c:if test="${not empty address}">
    <c:set var="countryName" value="${address.country.name}"/>
    <c:set var="city" value="${address.city}"/>
    <c:set var="street" value="${address.street}"/>
    <c:set var="zip_code" value="${address.zipCode}"/>
    <c:set var="apartmentNumber" value="${address.apartmentNumber}"/>
    <c:set var="houseNumber" value="${address.houseNumber}"/>
</c:if>>
<body>
<h2></h2>
<div class="container">
    <h1><fmt:message key="label.buyer.address"/></h1>
    <hr>
    <div class="row">
        <div class="col-md-9">
            <div class="col-md-10 col-md-offset-1">
                <c:if test="${not empty message}">

                    <c:forEach items="${message}" var="item" varStatus="status">
                        <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
                    </c:forEach>
                </c:if>
            </div>
            <form class="form-horizontal" role="form" action="<c:url value="/buyer/address"/>" method="post">
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.address.country"/></label>
                    <div class="col-lg-8">
                        <select id="country" name="country">
                            <c:forEach items="${countries}" var="country">
                                <c:remove var="selected"/>
                                <c:if test="${not empty countryName and countryName eq country}">
                                    <c:set var="selected" value="selected"/>
                                </c:if>
                                <option value="${country}" ${selected}>
                                    <c:out value=" ${country}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.city"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="city" name="city" value="${city}">
                    </div>
                </div>
                <div class=" form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.zip_code"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="zip_code" name="zip_code" value="${zip_code}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.street"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="street" name="street" value="${street}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label"><fmt:message key="label.register.apartNumber"/></label>
                    <div class="col-md-8">
                        <input class="form-control" type="text" id="apartmentNumber" name="apartmentNumber"
                               value="${apartmentNumber}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label"><fmt:message key="label.register.house_number"/></label>
                    <div class="col-md-8">
                        <input class="form-control" type="text" id="houseNumber" name="houseNumber"
                               value="${houseNumber}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="container-fluid">
                        <label class="col-md-3 control-label"> </label>
                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                key="label.confirm"/></button>
                    </div>
                </div>
            </form>
            <form action="<c:url value="/welcome"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.back"/></button>
            </form>
        </div>
    </div>
</div>
<hr>
</body>
</html>
