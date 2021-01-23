<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 28.12.2020
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<u:html title="Music Land">
    <div class="text-center">
        <img src='<c:url value="/img/main.jpg"></c:url>' alt="Shop" height="300" width="400"/>
        <jsp:include page="../parts/nav-bar.jsp">
            <jsp:param name="page" value="/welcome.html"/>
        </jsp:include>
    </div>
    <div class="container">
        <div class="vertical-center-row">
            <div class="page-header" style="">
                <h1 class="text-center">Music Land
                    <small><fmt:message key="label.welcome.small"/></small>
                </h1>
            </div>
            <h2 class="text-center"><fmt:message key="label.welcome.happy"/></h2>
            <div class="container-fluid">
                <c:if test="${sessionScope.authorizedUser == null}">
                    <c:url value="/login.html" var="loginUrl"/>
                    <a class="btn btn-primary" href="${loginUrl}"><fmt:message
                            key="label.title.login"/></a>
                </c:if>
                <c:url value="/products/list.html" var="listUrl"/>
                <a class="btn btn-primary" href="${listUrl}" role="button"><fmt:message
                        key="label.title.showProducts"/></a>
            </div>
        </div>
        <c:if test="${sessionScope.authorizedUser != null}">
            <br>
            <u:menu buttons="${menu}"/>
            <br>
        </c:if>
    </div>
    <c:import url="../parts/footer.jsp"/>
</u:html>
