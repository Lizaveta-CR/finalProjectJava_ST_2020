<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 03.01.2021
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="${cookie.lang.value}">
<head>
    <title>Login</title>
    <%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/jQuery/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/panel.css"/>
    <script src="${pageContext.request.contextPath}/js/preventRefresh.js"></script>
</head>
<body>
<script>
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.href);
    }
</script>
<div class="sidenav">
    <div class="login-main-text">
        <h2><fmt:message key="label.login.title"/></h2>
        <p><fmt:message key="label.login.title.second"/></p>
    </div>
</div>
<div class="main">
    <div class="row">
        <div class="col-md-4">
            <form action="${pageContext.request.contextPath}/registration" method="get">
                <button type="submit" class="btn btn-secondary"><fmt:message key="label.register.title"/></button>
            </form>
            <form action="<c:url value="/welcome"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.back"/></button>
            </form>
        </div>
    </div>
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
            <c:if test="${not empty message}">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                            <%--                        <c:forEach items="${redirectedData}" var="item" varStatus="status">--%>
                        <p class="bg-danger text-center lead"><c:out value="${message}"/></p>
                            <%--                            <p class="bg-danger text-center lead"><c:out value="${item.value}"/></p>--%>
                            <%--                        </c:forEach>--%>
                    </div>
                </div>
                <div style="text-align: center">
                    <a href="link">
                        <img src='<c:url value="/img/errorLogin.jpg"></c:url>' alt="error" height="100" width="100"/>
                    </a>
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group" act>
                    <label><fmt:message key="label.register.login"/></label>
                    <input type="text" class="form-control" placeholder=
                    <fmt:message key="label.register.login"/> id="login" name="login">
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.register.pass"/></label>
                    <input type="password" class="form-control" placeholder=
                    <fmt:message key="label.register.pass"/> id="password" name="password">
                </div>
                <button type="submit" class="btn btn-black"><fmt:message key="label.title.login"/></button>
            </form>
            <%--            <div class="container">--%>
            <%--            </div>--%>
        </div>
        <%--        <a href="${pageContext.request.contextPath}/index.jsp">Main page</a>--%>
    </div>
</div>
</body>
</html>
