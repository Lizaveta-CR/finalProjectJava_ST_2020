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
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<u:html title="Music Land" cssFile='main.css'>
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }
    </style>
    <c:set var="user" scope="session" value="${authorizedUser}"/>

    <%--    <div class="text-center">--%>
    <%--        <img src='<c:url value="/img/main.jpg"/>' alt="Shop" height="300" width="400"/>--%>
    <%--    </div>--%>
    <%--    <c:url value="/img/header.jpg" var="image"/>--%>
    <%--    <div class="container">--%>
    <div class="bg">
        <jsp:include page="../parts/nav-bar.jsp">
            <jsp:param name="page" value="/welcome.html"/>
        </jsp:include>
        <header class="text-left">
            <h1>Music Land
                <small><fmt:message key="label.welcome.small"/></small></h1>
            <pre><fmt:message key="label.welcome.main"/></pre>
            <c:if test="${not empty user}">
                <p class="lead" align="left">
                    <ctg:welcome name="${user.name}"/>
                </p>
            </c:if>
        </header>
    </div>
    <div class="container">
        <c:if test="${user != null}">
            <br>
            <u:menu buttons="${menu}"/>
            <br>
        </c:if>
        <c:import url="../parts/footer.jsp"/>
    </div>
</u:html>
