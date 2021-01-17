<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 10.01.2021
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>FeedBack</title>
    <u:head/>
</head>
<body>
<%@include file="/WEB-INF/jsp/parts/nav-bar.jsp" %>
<table class="table table-striped">
    <thead>
    <tr>
        <th><fmt:message key="label.product.model"/></th>
        <th><fmt:message key="label.product.price"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.model}</td>
            <td><fmt:formatNumber value="${product.price}" type="currency"/></td>
            <td>
                <form action="<c:url value="/buyer/feedBack/submit?productId=${product.id}"/>"
                      method="post">
                    <p><fmt:message key="label.product.value"/></p>
                    <p><input type="number" min="1" max="10" value="10" name="mark"
                              id="mark"/></p>
                    <button class="btn btn-info btn-block" type="submit"><fmt:message
                            key="label.product.mark"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
