<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 16.01.2021
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <title>Action Selector</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="label.chooseCookieLocale"/>
            <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="<c:url value="/changeLocale.html?cookieLocale=en_US"/>"><fmt:message
                    key="label.lang.en"/></a>
            </li>
            <li><a href="<c:url value="/changeLocale.html?cookieLocale=ru_RU"/>"><fmt:message
                    key="label.lang.ru"/></a>
            </li>
        </ul>
    </li>
    <c:if test="${authorizedUser!=null}">
        <li><a href="<c:url value="/logout.html"/>"><fmt:message
                key="label.title.logout"/></a></li>
    </c:if>
</ul>
</body>
</html>
