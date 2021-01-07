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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">

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
            <div class="col-xs-6 col-sm-3">
                <c:if test="${sessionScope.authorizedUser == null}">
                    <div class="col-xs-6 col-sm-3" style="background-color:lavender;">
                        <a class="btn btn-primary" href="<c:url value="/login"/>" role="button"><fmt:message
                                key="label.title.login"/></a>
                            <%--                            <form action="${pageContext.request.contextPath}/login" method="get">--%>
                            <%--                                <button type="submit" class="btn btn-outline-primary">Login</button>--%>
                            <%--                            </form>--%>
                    </div>
                </c:if>
                <div class="col-xs-6 col-sm-3">
                    <a class="btn btn-primary" href="<c:url value="/products/list"/>" role="button"><fmt:message
                            key="label.title.showProducts"/></a>
                </div>
                <c:if test="${sessionScope.authorizedUser != null}">
                    <div class="col-xs-6 col-sm-3" style="background-color:lavender;">
                        <a class="btn btn-primary" href="<c:url value="/logout"/>" role="button"><fmt:message
                                key="label.title.logout"/></a>
                            <%--                        <form action="${pageContext.request.contextPath}/logout" method="get">--%>
                            <%--                            <button type="submit" class="btn btn-outline-primary">Logout</button>--%>
                            <%--                        </form>--%>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
