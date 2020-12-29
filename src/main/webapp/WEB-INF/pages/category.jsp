<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 29.12.2020
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Categories</title>
    <head>
        <title>Music Land</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
</head>
<body>
<c:forEach items="${category}" var="parentItem">
    <div class="container">
        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" href="#collapse1">${parentItem.name}</a>
                    </h4>
                </div>
                <div id="collapse1" class="panel-collapse collapse">
                    <c:forEach items="${parentItem.components}" var="childItem">
                        <div class="panel-body"> ${childItem.name}</div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<%--<c:forEach var="component" items="${category}">--%>
<%--    ${component.}--%>
<%--    ${product.category}--%>
<%--    ${product.model}--%>
<%--    ${product.available}--%>
<%--    ${product.description}--%>
<%--    ${product.image_url}--%>
<%--    ${product.price}--%>
<%--</c:forEach>--%>
</body>
</html>
