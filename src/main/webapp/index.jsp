<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 29.12.2020
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<%--<h2>Hello World!</h2>--%>
<%--<c:url value="WEB-INF/pages/products.jsp" var="allproducts"/>--%>
<%--<form method="post" action=${allproducts}>--%>
<%--    <input type="hidden" name="command" value="all_products"/>--%>
<%--    <button type="submit">Show</button>--%>
<%--</form>--%>
<jsp:forward page="WEB-INF/pages/welcome.jsp"/>
</body>
</html>
