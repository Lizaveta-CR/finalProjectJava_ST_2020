<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 06.01.2021
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="f" uri="function" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Buyers</title>
    <%@include file="/WEB-INF/jsp/parts/head.jsp" %>
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="content">
    <div class="container">
        <div class="row">
            <c:if test="${not empty redirectedData}">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <c:forEach items="${redirectedData}" var="item" varStatus="status">
                            <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <c:forEach items="${buyers}" var="buyer">
                <div class="col-lg-4">
                    <div class="text-center card-box">
                        <div class="member-card pt-2 pb-2">
                            <div class="thumb-lg member-thumb mx-auto">
                                <img src="<c:url value="/img/profile.jpg"/>"
                                     class="rounded-circle img-thumbnail" alt="profile-image" height="200" width="200">
                            </div>
                            <div class="">
                                <h4>${buyer.email}</h4>
                                <p class="text-muted"><span>| </span><span>
<%--                                    TODO:написать письмо--%>
<%--                                    <a href="#" class="text-pink">${buyer.telephone}</a></span>--%>
                                </p>
                            </div>
                                <%--                            <button type="button"--%>
                                <%--                                    class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">--%>
                                <%--                                Message Now--%>
                                <%--                            </button>--%>
                            <div class="mt-4">
                                <div class="row">
                                    <div class="col-4">
                                        <div class="mt-3">
                                            <p class="mb-0 text-muted"><fmt:message key="label.admin.buyer.access"/></p>
                                            <c:set var="enabled" scope="request" value="${buyer.enabled}"/>
                                            <c:choose>
                                                <c:when test="${enabled}">
                                                    <h4><fmt:message key="label.admin.buyer.have"/></h4>
                                                    <form action="<c:url value="/admin/buyers/disable?buyerId=${buyer.id}"/>"
                                                          method="post">
                                                        <button class="btn btn-primary" type="submit"><fmt:message
                                                                key="label.admin.buyer.disable"/></button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <h4><fmt:message key="label.admin.buyer.no"/></h4>
                                                    <form action="<c:url value="/admin/buyers/enable?buyerId=${buyer.id}"/>"
                                                          method="post">
                                                        <button class="btn btn-primary" type="submit"><fmt:message
                                                                key="label.admin.buyer.enable"/></button>
                                                    </form>
                                                    <fmt:message key="label.buyer.address.title.new" var="title"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <div class="mt-3">
                                            <h4>${fn:length(buyer.orders)}</h4>
                                            <p class="mb-0 text-muted"><fmt:message
                                                    key="label.admin.buyer.total.orders"/></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <ul class="pagination">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <div class="btn-group">
                            <li>
                                <button class="btn btn-primary" type="submit">${i}</button>
                            </li>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="btn-group">
                            <li>
                                <form action="<c:url value="/admin/buyers?page=${i}"/>" method="post">
                                    <button class="btn btn-primary" type="submit">${i}</button>
                                </form>
                            </li>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
    <form action="<c:url value="/welcome"/>" method="get">
        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                key="label.button.back"/></button>
    </form>
    <form action="<c:url value="/admin/generate"/>" method="post">
        <p><input type="number" min="0" value="1" name="amount" id="amount"/></p>
        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                key="label.button.generate"/></button>
    </form>
</div>
</body>
</html>
