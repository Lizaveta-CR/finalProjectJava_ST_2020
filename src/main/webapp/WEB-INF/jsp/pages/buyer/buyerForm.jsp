<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 05.01.2021
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Buyer</title>
    <%--    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">--%>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/jQuery/jquery-3.5.1.min.js"></script>
</head>
<body>
<c:set var="user" scope="session" value="${sessionScope.authorizedUser}"/>
<c:set var="buyer" scope="session" value="${sessionScope.authorizedBuyer}"/>
<div class="container" style="background-image:  url('<c:url value="/img/back.jpg"/>');">
    <h2 class="text-center"><ctg:welcome name="${user.name}"/></h2>
    <div class="col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
        <%--        <div class="well profile">--%>
        <div class="col-sm-12">
            <div class="col-xs-12 col-sm-8">
                <br>
                <p><strong><fmt:message key="label.register.email"/>: </strong> <c:out
                        value="${buyer.email}"/>
                </p>
                <p><strong><fmt:message key="label.register.telephone"/>: </strong> <c:out
                        value="${buyer.telephone}"/></p>
                <p><strong><fmt:message key="label.register.balance"/>: </strong><c:out
                        value="${buyer.balance}"/>
                </p>
                <p><strong><fmt:message key="label.register.bonus"/>: </strong><c:out
                        value="${buyer.bonus}"/>
                </p>
            </div>
        </div>
        <div class="col-xs-12 divider text-center">
            <div class="col-xs-12 col-sm-4 emphasis">
                <form action="<c:url value="/buyer/edit"/>" method="get">
                    <button class="btn btn-info btn-block" type="submit"><fmt:message
                            key="label.buyer.edit"/></button>
                </form>
            </div>
            <div class="col-xs-12 col-sm-4 emphasis">
                <form action="<c:url value="/buyer/address"/>" method="get">
                    <button class="btn btn-info btn-block" type="submit"><fmt:message
                            key="label.buyer.address"/></button>
                </form>
            </div>
            <div class="col-xs-12 col-sm-4 emphasis">
                <form action="<c:url value="/buyer/order"/>" method="get">
                    <button class="btn btn-info btn-block" type="submit"><fmt:message
                            key="label.buyer.button.order"/></button>
                </form>
            </div>
            <div class="col-xs-12 col-sm-4 emphasis">
                <form action="<c:url value="/welcome"/>" method="get">
                    <button class="btn btn-info btn-block" type="submit"><fmt:message
                            key="label.button.back"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
<%--TODO: pagination for viewing orders--%>

</body>
</html>
