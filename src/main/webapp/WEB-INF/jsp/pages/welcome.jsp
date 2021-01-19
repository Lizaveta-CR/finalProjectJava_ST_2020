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
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<%--<html lang="${cookie.lang.value}">--%>
<u:html title="Music Land">
    <%--<head>--%>
    <%--    <title>Music Land</title>--%>
    <%--    <u:head/>--%>
    <%--</head>--%>
    <%--<body>--%>
    <div class="text-center">
        <img src='<c:url value="/img/main.jpg"></c:url>' alt="Shop" height="300" width="400"/>
        <%@include file="../parts/nav-bar.jsp" %>
    </div>

    <div class="container">
        <div class="vertical-center-row">
            <div class="page-header" style="">
                <h1 class="text-center">Music Land
                    <small><fmt:message key="label.welcome.small"/></small>
                </h1>
            </div>
            <h2 class="text-center"><fmt:message key="label.welcome.happy"/></h2>
            <div class="container-fluid">
                <c:if test="${sessionScope.authorizedUser == null}">
                    <%--                <form action="<c:url value="/controller"/>">--%>
                    <%--                    <input type="hidden" name="command" value="/login">--%>
                    <%--                    <button class="btn btn-primary" type="submit"><fmt:message--%>
                    <%--                            key="label.title.login"/></button>--%>
                    <%--                </form>--%>
                    <c:url value="/login.html" var="loginUrl"/>
                    <a class="btn btn-primary" href="${loginUrl}"><fmt:message
                            key="label.title.login"/></a>
                </c:if>
                <c:url value="/products/list.html" var="listUrl"/>
                <a class="btn btn-primary" href="${listUrl}" role="button"><fmt:message
                        key="label.title.showProducts"/></a>
            </div>
        </div>
        <c:if test="${sessionScope.authorizedUser != null}">
            <br>
            <u:menu buttons="${menu}"/>
            <br>
        </c:if>
    </div>
    </div>
    </div>
    <%--    </body>--%>
</u:html>
