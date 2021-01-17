<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 09.01.2021
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html>
<head>
    <title>Order</title>
    <u:head/>
</head>
<body>
<%@include file="/WEB-INF/jsp/parts/nav-bar.jsp" %>
<c:choose>
    <c:when test="${not empty order.productIts and not empty orderItem}">
        <div class="container">
            <div class="row">
                <div class="col-xs-8">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="panel-title">
                                <div class="row">
                                    <div class="col-xs-6">
                                        <h5><span class="glyphicon glyphicon-shopping-cart"></span><fmt:message
                                                key="label.order.title"/></h5>
                                    </div>
                                    <div class="col-xs-6">
                                        <form action="<c:url value="/products/list"/>" method="get">
                                            <button class="btn btn--radius-2 btn--blue-2" type="submit">
                                                <span class="glyphicon glyphicon-share-alt"></span><fmt:message
                                                    key="label.button.continue.shop"/>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <c:forEach items="${order.productIts.iterator()}" var="product">
                                <div class="row">
                                    <c:choose>
                                        <c:when test="${not empty product.imageUrl}">
                                            <c:url value="/img/${product.imageUrl}" var="image"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url value="/img/main.jpg" var="image"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="col-xs-2"><img class="img-responsive" src="${image}"/>
                                    </div>
                                    <div class="col-xs-4">
                                        <h4 class="product-name"><strong>${product.model}</strong></h4><h4>
                                        <small>${product.description}</small></h4>
                                    </div>
                                    <div class="col-xs-6">
                                        <div class="col-xs-6 text-right">
                                            <h6><strong>${product.price} <span class="text-muted">x</span>
                                                <c:out value="${orderItem[product]}"/></strong></h6>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <button type="button" class="btn btn-link btn-xs">
                                            <form action="<c:url value="/buyer/order/remove?productId=${product.id}"/>"
                                                  method="post">
                                                <button class="btn btn-info btn-block" type="submit"><fmt:message
                                                        key="label.product.remove"/></button>
                                            </form>
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                            <hr>
                        </div>
                        <div class="panel-footer">
                            <div class="row text-center">
                                <div class="col-xs-9">
                                    <h4 class="text-right"><fmt:message key="label.order.total"/> <strong><c:out
                                            value="${order.price}"/></strong></h4>
                                </div>
                                <div class="col-xs-3">
                                    <form action="<c:url value="/buyer/order/submit"/>" method="get">
                                        <button class="btn btn-success btn-block" type="submit">
                                            <fmt:message key="label.confirm"/>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-footer">
            <form action="<c:url value="/products/list"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.back"/></button>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container text-center">
            <h2><fmt:message key="label.title.oop"/></h2>
            <div class="col-md-11 col-md-offset-3">
                <div class="row">
                    <div class="col-md-7 text-center">
                        <div class="thumbnail">
                            <img src="<c:url value="/img/empty.jpg"/>" alt="Empty" style="width:100%">
                            <div class="caption">
                                <p><fmt:message key="label.order.empty"/></p>
                                <form action="<c:url value="/products/list"/>" method="get">
                                    <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                            key="label.button.fix"/></button>
                                </form>
                                <div class="card-footer">
                                    <form action="<c:url value="/buyer/buyerForm"/>" method="get">
                                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                                key="label.button.back"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
