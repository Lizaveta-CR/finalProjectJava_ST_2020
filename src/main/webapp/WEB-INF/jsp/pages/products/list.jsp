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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<%--TODO:--%>
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
                        <div class="panel-body">
                            <table class="table">
                                <caption>${childItem.name}</caption>
                                <thead>
                                <tr>
                                    <th scope="col"><fmt:message
                                            key="label.product.picture"/></th>
                                    <th scope="col"><fmt:message
                                            key="label.product.model"/></th>
                                    <th scope="col"><fmt:message
                                            key="label.product.description"/></th>
                                    <th scope="col"><fmt:message
                                            key="label.product.price"/></th>
                                    <th scope="col"><fmt:message
                                            key="label.product.rate"/></th>
                                </tr>
                                </thead>
                                <c:forEach var="product" items="${childItem.products}">
                                    <c:choose>
                                        <c:when test="${not empty product.imageUrl}">
                                            <c:url value="/img/${product.imageUrl}" var="image"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url value="/img/main.jpg" var="image"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <img src="${image}" class="img-thumbnail" height="200" width="300"/>
                                        </td>
                                        <td> ${product.model}</td>
                                        <td> ${product.description}</td>
                                        <td> ${product.price}</td>
                                        <c:choose>
                                            <c:when test="${not empty rateMap[product.id]}">
                                                <c:url value=" ${rateMap[product.id]}" var="mark"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="mark" scope="page" value="-"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <td> ${mark}</td>
                                        <c:if test="${sessionScope.authorizedUser != null}">
                                            <td>
                                                <form action="<c:url value="/products/buy?productId=${product.id}"/>"
                                                      method="post">
                                                    <p><fmt:message key="label.product.amount"/></p>
                                                    <p><input type="number" min="1" value="1" name="amount"
                                                              id="amount"/></p>
                                                    <button class="btn btn-info btn-block" type="submit"><fmt:message
                                                            key="label.product.buy"/></button>
                                                </form>
                                                </form>
                                            </td>
                                        </c:if>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<div class="col-md-4">
    <form action="<c:url value="/welcome"/>" method="get">
        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                key="label.button.back"/></button>
    </form>
</div>
</body>
</html>
