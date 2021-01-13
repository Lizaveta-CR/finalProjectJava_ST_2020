<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 28.12.2020
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="${cookie.lang.value}">
<head>
    <title>Music Land</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="text-center">
    <img src='<c:url value="/img/main.jpg"></c:url>' alt="Shop" height="300" width="400"/>
    <jsp:include page="../parts/nav-bar-language.jsp"/>
</div>
<div class="container">
    <div class="vertical-center-row">
        <div class="page-header" style="">
            <h1 class="text-center">Music Land
                <small><fmt:message key="label.welcome.small"/></small>
            </h1>
        </div>
        <h2 class="text-center"><fmt:message key="label.welcome.happy"/></h2>
        <%--        <h3 class="text-center">Choose what you want to do:</h3>--%>
        <div class="container-fluid">
            <c:if test="${sessionScope.authorizedUser == null}">
                <a class="btn btn-primary" href="<c:url value="/login"/>" role="button"><fmt:message
                        key="label.title.login"/></a>
            </c:if>
            <a class="btn btn-primary" href="<c:url value="/products/list"/>" role="button"><fmt:message
                    key="label.title.showProducts"/></a>
        </div>
    </div>
    <c:if test="${sessionScope.authorizedUser != null}">
        <div class="container-fluid">
            <c:url value="${menu.url}" var="itemUrl"/>
            <br>
            <form action="${itemUrl}" method="get">
                <button class="btn btn-primary" type="submit"><fmt:message
                        key="label.welcome.myPage"/></button>
            </form>
            <a class="btn btn-primary" href="<c:url value="/logout"/>" role="button"><fmt:message
                    key="label.title.logout"/></a>
        </div>
    </c:if>
</div>
</div>
<%@ include file="../parts/footer.jsp" %>
</div>
</body>
</html>
