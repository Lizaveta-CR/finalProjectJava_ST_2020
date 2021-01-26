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
<%@ taglib prefix="f" uri="function" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="i18n.messages"/>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:html title="Action Selector">
    <ul class="nav nav-tabs">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="label.chooseCookieLocale"/>
                <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="<c:url value="/changeLocale.html?cookieLocale=en_US&page=${param.page}"/>">
                        <img src="/img/language/usa.svg" alt="English" width="20" height="15">
                        <fmt:message key="label.lang.en"/></a>
                </li>
                <li><a href="<c:url value="/changeLocale.html?cookieLocale=ru_RU&page=${param.page}"/>">
                    <img src="/img/language/russian.png" alt="Russian" width="20" height="15">
                    <fmt:message
                            key="label.lang.ru"/></a>
                </li>
                <li><a href="<c:url value="/changeLocale.html?cookieLocale=be_BY&page=${param.page}"/>">
                    <img src="/img/language/belarus.png" alt="Belorussian" width="20" height="15">
                    <fmt:message
                            key="label.lang.by"/></a>
                </li>
            </ul>
        </li>
        <c:choose>
            <c:when test="${authorizedUser!=null}">
                <li><a href="<c:url value="/logout.html"/>"><fmt:message
                        key="label.title.logout"/></a></li>
            </c:when>
            <c:otherwise> <c:url value="/login.html" var="loginUrl"/>
                <li>
                    <a href="${loginUrl}"><fmt:message
                            key="label.title.login"/></a></li>
            </c:otherwise>

        </c:choose>
        <li>
            <c:if test="${not f:equals(param.page, '/welcome.html')}">
                <a href="<c:url value="/welcome.html"/>"><fmt:message
                        key="label.button.back"/></a>
            </c:if>
        </li>
        <c:url value="/products/list.html" var="listUrl"/>
        <c:if test="${not f:equals(param.page, '/products/list.html')}">
            <li><a href="${listUrl}" role="button"><fmt:message
                    key="label.title.showProducts"/></a></li>
        </c:if>

    </ul>
</u:html>
