<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 06.01.2021
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>--%>
<%--<%@ taglib prefix="f" uri="function" %>--%>
<%--<%@ taglib prefix="ctg" uri="customtags" %>--%>

<%--<fmt:setLocale value="${cookie.lang.value}"/>--%>
<%--<fmt:setBundle basename="i18n.messages"/>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Admin</title>--%>
<%--    <%@include file="/WEB-INF/jsp/parts/head.jsp" %>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h2 class="text-center"><ctg:welcome name="${authorizedUser.name}"/></h2>--%>

<%--<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">--%>
<%--<div class="content">--%>
<%--    <div class="container">--%>
<%--        <div class="row">--%>
<%--            <c:forEach items="${buyers}" var="buyer">--%>
<%--                <div class="col-lg-4">--%>
<%--                    <div class="text-center card-box">--%>
<%--                        <div class="member-card pt-2 pb-2">--%>
<%--                            <div class="thumb-lg member-thumb mx-auto">--%>
<%--                                <img src="<c:url value="/img/profile.jpg"/>"--%>
<%--                                     class="rounded-circle img-thumbnail" alt="profile-image" height="200" width="200">--%>
<%--                            </div>--%>
<%--                            <div class="">--%>
<%--                                <h4>${buyer.email}</h4>--%>
<%--                                <p class="text-muted"><span>| </span><span>--%>
<%--&lt;%&ndash;                                    TODO:написать письмо&ndash;%&gt;--%>
<%--&lt;%&ndash;                                    <a href="#" class="text-pink">${buyer.telephone}</a></span>&ndash;%&gt;--%>
<%--                                </p>--%>
<%--                            </div>--%>
<%--                                &lt;%&ndash;                            <button type="button"&ndash;%&gt;--%>
<%--                                &lt;%&ndash;                                    class="btn btn-primary mt-3 btn-rounded waves-effect w-md waves-light">&ndash;%&gt;--%>
<%--                                &lt;%&ndash;                                Message Now&ndash;%&gt;--%>
<%--                                &lt;%&ndash;                            </button>&ndash;%&gt;--%>
<%--                            <div class="mt-4">--%>
<%--                                <div class="row">--%>
<%--                                    <div class="col-4">--%>
<%--                                        <div class="mt-3">--%>
<%--                                            <p class="mb-0 text-muted"><fmt:message key="label.admin.buyer.access"/></p>--%>
<%--                                            <c:set var="enabled" scope="request" value="${buyer.enabled}"/>--%>
<%--                                            <c:choose>--%>
<%--                                                &lt;%&ndash;                                                TODO: writes "false"&ndash;%&gt;--%>
<%--                                                <c:when test="${enabled}">--%>
<%--                                                    <h4>${enabled}</h4>--%>
<%--                                                    <form action="<c:url value="/admin/adminForm/disable?buyerId=${buyer.id}"/>"--%>
<%--                                                          method="post">--%>
<%--                                                        <button class="btn btn-primary" type="submit"><fmt:message--%>
<%--                                                                key="label.admin.buyer.disable"/></button>--%>
<%--                                                    </form>--%>
<%--                                                </c:when>--%>
<%--                                                <c:otherwise>--%>
<%--                                                    <h4>${enabled}</h4>--%>
<%--                                                    <form action="<c:url value="/admin/adminForm/enable?buyerId=${buyer.id}"/>"--%>
<%--                                                          method="post">--%>
<%--                                                        <button class="btn btn-primary" type="submit"><fmt:message--%>
<%--                                                                key="label.admin.buyer.enable"/></button>--%>
<%--                                                    </form>--%>
<%--                                                    <fmt:message key="label.buyer.address.title.new" var="title"/>--%>
<%--                                                </c:otherwise>--%>
<%--                                            </c:choose>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                    <div class="col-4">--%>
<%--                                        <div class="mt-3">--%>
<%--                                            <h4>${fn:length(buyer.orders)}</h4>--%>
<%--                                            <p class="mb-0 text-muted"><fmt:message--%>
<%--                                                    key="label.admin.buyer.total.orders"/></p>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
<%--        </div>--%>
<%--        <ul class="pagination">--%>
<%--            <c:forEach begin="1" end="${noOfPages}" var="i">--%>
<%--                <c:choose>--%>
<%--                    <c:when test="${currentPage eq i}">--%>
<%--                        <div class="btn-group">--%>
<%--                            <li>--%>
<%--                                <button class="btn btn-primary" type="submit">${i}</button>--%>
<%--                            </li>--%>
<%--                        </div>--%>
<%--                    </c:when>--%>
<%--                    <c:otherwise>--%>
<%--                        <div class="btn-group">--%>
<%--                            <li>--%>
<%--                                <form action="<c:url value="/admin/adminForm?page=${i}"/>" method="post">--%>
<%--                                    <button class="btn btn-primary" type="submit">${i}</button>--%>
<%--                                </form>--%>
<%--                            </li>--%>
<%--                        </div>--%>
<%--                    </c:otherwise>--%>
<%--                </c:choose>--%>
<%--            </c:forEach>--%>
<%--        </ul>--%>
<%--    </div>--%>
<%--    <form action="<c:url value="/welcome"/>" method="get">--%>
<%--        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message--%>
<%--                key="label.button.back"/></button>--%>
<%--    </form>--%>
<%--    <!-- container -->--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
