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
</head>
<body>
<c:forEach items="${category}" var="parentItem">
    ${parentItem.name}
    <c:forEach items="${parentItem.components}" var="childItem">
        ${childItem.name}
        <%--        <c:forEach items="${childItem.components}" var="grandchildItem">--%>
        <%--            ${grandchildItem.name}--%>
        <%--        </c:forEach>--%>
    </c:forEach>
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
