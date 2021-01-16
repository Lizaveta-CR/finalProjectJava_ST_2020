<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="buttons" required="true" rtexprvalue="true" type="java.util.List" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<%--<html lang="${cookie.lang.value}">--%>
<%--<head>--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <link href="<c:url value="/bootstrap/css/bootstrap.css"/>>">--%>
<%--    <script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>--%>
<%--    <script src="<c:url value="/bootstrap/jQuery/jquery-3.5.1.min.js"/>"></script>--%>
<%--</head>--%>
<%--<body>--%>

<div class="btn-group btn-group-justified">
    <c:forEach var="itemUrl" items="${buttons}">
        <a href="<c:url value="${itemUrl.url}"/>" class="btn btn-primary"><fmt:message
                key="${itemUrl.name}"/></a>
    </c:forEach>
    <%--    <jsp:doBody/>--%>
</div>
<%--</body>--%>
<%--</html>--%>