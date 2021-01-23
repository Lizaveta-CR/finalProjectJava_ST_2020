<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 27.12.2020
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<u:html title="Error" cssFile="error.css">
    <div class="moon"></div>
    <div class="moon__crater moon__crater1"></div>
    <div class="moon__crater moon__crater2"></div>
    <div class="moon__crater moon__crater3"></div>

    <div class="star star1"></div>
    <div class="star star2"></div>
    <div class="star star3"></div>
    <div class="star star4"></div>
    <div class="star star5"></div>

    <div class="error">
        <c:set var="errorData" value="${pageContext.errorData}"/>
        <div class="error__title">${errorData.statusCode}</div>
        <div class="error__subtitle">Hmmm...</div>
        <div class="error__description">
            <c:choose>
                <c:when test="${not empty error}">
                    ${error}
                </c:when>
                <c:when test="${not empty errorData.requestURI}">
                    <fmt:message key="label.error.requested"/> ${errorData.requestURI} <fmt:message
                        key="label.error.not.found"/>
                </c:when>
                <c:otherwise><fmt:message key="label.error.unpredictable"/></c:otherwise>
            </c:choose>
        </div>
        <form method="get" action="<c:url value="/index.html"/>">
            <button class="error__button error__button--active"><fmt:message
                    key="label.main"/></button>
        </form>
    </div>

    <div class="astronaut">
        <div class="astronaut__backpack"></div>
        <div class="astronaut__body"></div>
        <div class="astronaut__body__chest"></div>
        <div class="astronaut__arm-left1"></div>
        <div class="astronaut__arm-left2"></div>
        <div class="astronaut__arm-right1"></div>
        <div class="astronaut__arm-right2"></div>
        <div class="astronaut__arm-thumb-left"></div>
        <div class="astronaut__arm-thumb-right"></div>
        <div class="astronaut__leg-left"></div>
        <div class="astronaut__leg-right"></div>
        <div class="astronaut__foot-left"></div>
        <div class="astronaut__foot-right"></div>
        <div class="astronaut__wrist-left"></div>
        <div class="astronaut__wrist-right"></div>

        <div class="astronaut__cord">
            <canvas id="cord" height="500px" width="500px"></canvas>
        </div>

        <div class="astronaut__head">
            <canvas id="visor" width="60px" height="60px"></canvas>
            <div class="astronaut__head-visor-flare1"></div>
            <div class="astronaut__head-visor-flare2"></div>
        </div>
    </div>
    <script>
        <jsp:directive.include file="/js/error.js"/>
    </script>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>
