<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 05.01.2021
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ page session="true" %>
<html>
<html>
<head>
    <title>Buyer</title>
</head>
<body>
<ctg:welcome name="${sessionScope.authorizedUser.name}"/>
buyer
<a href="/index.jsp">main</a>
</body>
</html>
