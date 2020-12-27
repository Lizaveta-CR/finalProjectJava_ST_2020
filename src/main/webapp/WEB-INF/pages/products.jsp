<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 27.12.2020
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Products</title>
</head>
<body>

<c:forEach var="product" items="${products}">
    ${product.id}
    ${product.category}
    ${product.model}
    ${product.available}
    ${product.description}
    ${product.image_url}
    ${product.price}
</c:forEach>
</body>
</html>
