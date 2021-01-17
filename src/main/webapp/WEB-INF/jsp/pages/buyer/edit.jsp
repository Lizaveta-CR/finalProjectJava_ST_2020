<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 08.01.2021
  Time: 12:47
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
    <title>Edit profile</title>
    <link href="${pageContext.request.contextPath}/css/main.css">
    <u:head/>
</head>
<body>
<%@include file="/WEB-INF/jsp/parts/nav-bar.jsp" %>
<c:choose>
    <c:when test="${not empty buyerInfo}">
        <c:set var="login" value="${sessionScope.authorizedUser.login}"/>
        <c:set var="email" value="${buyerInfo.email}"/>
        <c:set var="telephone" value="${buyerInfo.telephone}"/>
        <c:set var="balance" value="${buyerInfo.balance}"/>
    </c:when>
</c:choose>
<div class="container">
    <h1><fmt:message key="label.buyer.edit.profile"/></h1>
    <hr>
    <div class="row">
        <div class="col-md-9">
            <div class="col-md-10 col-md-offset-1">
                <c:if test="${not empty message}">

                    <c:forEach items="${message}" var="item" varStatus="status">
                        <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
                    </c:forEach>
                </c:if>
            </div>
            <h3><fmt:message key="label.buyer.edit.title"/></h3>
            <form name="myform" class="form-horizontal" role="form" action="<c:url value="/buyer/edit"/>" method="post"
                  onsubmit="return validateform()">
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.login"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="login" name="login" value="${login}"
                               pattern="[A-Za-z0-9_]{1,15}" title="<fmt:message key="label.login.pattern"/>"
                               required
                               oninvalid="this.setCustomValidity('<fmt:message key="label.login.pattern"/>')"
                               oninput="setCustomValidity('')"
                        >
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.email"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="email" name="email" value="${email}"
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" title="<fmt:message key="label.email.pattern"/>"
                               required
                               oninvalid="this.setCustomValidity('<fmt:message key="label.email.pattern"/>')"
                               oninput="setCustomValidity('')">
                    </div>
                </div>
                <div class=" form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.telephone"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="number" id="telephone" name="telephone" value="${telephone}"
                               required=""
                               oninvalid="this.setCustomValidity('<fmt:message key="label.telephone.pattern"/>')"
                               oninput="setCustomValidity('')">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><fmt:message key="label.register.balance"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" id="balance" name="balance" value="${balance}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="container-fluid">
                        <label class="col-md-3 control-label"> </label>

                        <button class="btn btn--radius-2 btn--blue-2" type="submit" id="submit"><fmt:message
                                key="label.confirm"/></button>

                    </div>
                </div>
            </form>
            <form action="<c:url value="/welcome"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.back"/></button>
            </form>
            <form action="<c:url value="/buyer/editPass"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.edit.pass"/></button>
            </form>
        </div>
    </div>
</div>
<hr>
</body>
</html>
