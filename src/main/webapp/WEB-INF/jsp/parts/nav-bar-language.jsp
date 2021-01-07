<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 07.01.2021
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Language Selector</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="label.chooseCookieLocale"/>
            <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="<c:url value="/changeLocale?cookieLocale=en_US"/>"><fmt:message key="label.lang.en"/></a></li>
            <li><a href="<c:url value="/changeLocale?cookieLocale=ru_RU"/>"><fmt:message key="label.lang.ru"/></a></li>
        </ul>
    </li>
    <%--    <li><a href="#">Menu 2</a></li>--%>
    <%--    <li><a href="#">Menu 3</a></li>--%>
</ul>
</body>
</html>
