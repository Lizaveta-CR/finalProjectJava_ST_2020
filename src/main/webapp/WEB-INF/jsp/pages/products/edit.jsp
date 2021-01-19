<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 13.01.2021
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="f" uri="function" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Admin</title>
    <u:head/>
<%--    <%@include file="/WEB-INF/jsp/parts/head.jsp" %>--%>
</head>
<body>
<%@include file="/WEB-INF/jsp/parts/nav-bar.jsp" %>
<c:choose>
    <c:when test="${not empty product}">
        <c:set var="product" value="${product}"/>
        <c:set var="available" value="${product.available}"/>
        <c:set var="description" value="${product.description}"/>
        <c:set var="price" value="${product.price}"/>
    </c:when>
</c:choose>
<div class="container">
    <h1><fmt:message key="label.product.edit"/></h1>
    <hr>
    <div class="row">
        <div class="col-md-9">
            <div class="col-md-10 col-md-offset-1">
                <c:if test="${not empty redirectedData}">

                    <c:forEach items="${redirectedData}" var="item" varStatus="status">
                        <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
                    </c:forEach>
                </c:if>
            </div>
            <form class="form-horizontal" role="form" action="<c:url value="/products/edit.html"/>" method="post"
                  enctype="multipart/form-data">
                <div class="custom-control custom-checkbox">
                    <input type="checkbox" class="custom-control-input" name="access" id="access"
                           value="${available}">
                    <label class="custom-control-label" for="access"><fmt:message
                            key="label.product.available"/></label>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.product.description"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="description" name="description"
                               value="${description}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.product.file"/></label>
                    <div class="col-lg-8">
                        <input type="button" id="upload" value="<fmt:message key="label.file.upload"/>"
                               onclick="document.getElementById('file').click();"/>
                        <input type="file" style="display:none;" id="file" name="file"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-8">
                        <label class="col-lg-3 control-label"><fmt:message key="label.product.description"/></label>
                        <input type="hidden" class="form-control" type="text" id="productId" name="productId"
                               value="${product.id}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.product.price"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="price" name="price" value="${price}" placeholder="">
                    </div>
                </div>
                <div class="form-group">
                    <div class="container-fluid">
                        <label class="col-md-3 control-label"> </label>
                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                key="label.confirm"/></button>
                    </div>
                </div>
            </form>
            <form action="<c:url value="/welcome.html"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.back"/></button>
            </form>
        </div>
    </div>
</div>
<hr>
</body>
</html>
<script>
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.href);
    }
</script>