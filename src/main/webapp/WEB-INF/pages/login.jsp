<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 03.01.2021
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/jQuery/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
</head>
<body>
<div class="sidenav">
    <div class="login-main-text">
        <h2>Login Page</h2>
        <p>Login or register from here to access.</p>
        ${message}
        <c:set var="message" value='${requestScope["message"]}'/>
        <c:out value="${message}"/>
        ${requestScope.message}
<%--        <%= request.getAttribute("message") %>--%>
    </div>
</div>
<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
            <%--            <c:out value="${param.message}"/>--%>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group" act>
                    <label>Login</label>
                    <input type="text" class="form-control" placeholder="User Name" id="login" name="login">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" placeholder="Password" id="password" name="password">
                </div>
                <button type="submit" class="btn btn-black">Login</button>
                <button type="submit" class="btn btn-secondary">Register</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
