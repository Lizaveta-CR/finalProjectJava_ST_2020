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
    <script src="${pageContext.request.contextPath}/js/preventRefresh.js"></script>
</head>
<body>
<c:if test="${not empty message}">
    <script>alert("${message}");</script>
</c:if>
<div class="sidenav">
    <div class="login-main-text">
        <h2>Login Page</h2>
        <p>Login or register from here to access.</p>
    </div>
</div>
<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
            <c:if test="${not empty message}">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <p class="bg-danger text-center lead"><c:out value="${message}"/></p>
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
                    <label>Login</label>
                    <input type="text" class="form-control" placeholder="User Name" id="login" name="login" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" placeholder="Password" id="password" name="password"
                           required oninvalid="this.setCustomValidity('Fill all fields!')">
                </div>
                <button type="submit" class="btn btn-black">Login</button>
                <button type="submit" class="btn btn-secondary">Register</button>
            </form>
            <script>
                preventRefresh()
            </script>
        </div>
        <a href="${pageContext.request.contextPath}/index.jsp">Main page</a>
    </div>
</div>
</body>
</html>
