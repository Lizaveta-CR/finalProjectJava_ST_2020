<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="cssFiles" required="false" rtexprvalue="true" type="java.util.List" %>
<%@attribute name="cssFile" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="fontFile" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@attribute name="jsFile" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link rel="shortcut icon" href="<c:url value="/img/favicon/favicon.ico"/>" type="image/x-icon"/>
    <c:if test="${not empty cssFiles}">
        <c:forEach var="cssFileItem" items="${cssFiles}">
            <link href="<c:url value="/css/${cssFileItem}"/>" rel="stylesheet">
        </c:forEach>
    </c:if>
    <c:if test="${not empty cssFile}">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/${cssFile}"/>">
    </c:if>
    <c:if test="${not empty fontFile}">
        <link href="<c:url value="/bootstrap/fonts/${fontFile}"/>" rel="stylesheet">
    </c:if>
    <c:if test="${not empty jsFile}">
        <script type="text/javascript" src="<c:url value="/js/${jsFile}"/>"></script>
    </c:if>
</head>
<body>
<jsp:doBody/>
</body>
</html>