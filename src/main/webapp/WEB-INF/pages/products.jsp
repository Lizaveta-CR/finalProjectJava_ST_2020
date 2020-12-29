<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 27.12.2020
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="ctg" uri="custom" %>--%>
<%@taglib uri="/WEB-INF/tld/custom.tld" prefix="ctg" %>

<html>
<head>
    <title>Products</title>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">--%>
    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
    <%--    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>--%>
</head>
<body>
<%--<img src="<c:url value="/img/main.jpg"/>" alt="Store"/>--%>
<%--<img src="/Users/elizaveta/IdeaProjects/finalProjectJava_ST_2020/src/main/webapp/img/main.jpg" alt="Store"/>--%>
<c:forEach var="product" items="${products}">
    ${product.id}
    ${product.category}
    ${product.model}
    ${product.available}
    ${product.description}
    ${product.image_url}
    ${product.price}
</c:forEach>
<ctg:info/>

</body>
</html>
