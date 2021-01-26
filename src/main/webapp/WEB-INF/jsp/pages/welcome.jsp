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
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<u:html title="Music Land" cssFile='main.css'>
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }
    </style>
    <c:set var="user" scope="session" value="${authorizedUser}"/>
    <div class="bg">
        <jsp:include page="../parts/nav-bar.jsp">
            <jsp:param name="page" value="/welcome.html"/>
        </jsp:include>

        <header class="text-left">
            <h1>Music Land <small><fmt:message key="label.welcome.small"/></small></h1>
            <pre style="border: 0; background-color: transparent;"><fmt:message key="label.welcome.main"/></pre>
            <c:if test="${not empty user}">
                <p class="lead" align="left">
                    <ctg:welcome name="${user.name}"/>
                </p>
            </c:if>
            <div class="row">
                <div class="col-md-3">
                    <div class="container" style="height: 450px;width: 450px">
                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                <li data-target="#myCarousel" data-slide-to="1"></li>
                                <li data-target="#myCarousel" data-slide-to="2"></li>
                            </ol>

                            <div class="carousel-inner">
                                <div class="item active">
                                    <img src="<c:url value="/img/city/la.jpg"/>" alt="Los Angeles"
                                         style="width:100%;">
                                </div>

                                <div class="item">
                                    <img src="<c:url value="/img/city/chicago.jpg"/>" alt="Chicago"
                                         style="width:100%;">
                                </div>

                                <div class="item">
                                    <img src="<c:url value="/img/city/ny.jpg"/>" alt="New york"
                                         style="width:100%;">
                                </div>
                            </div>

                            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                                <span class="sr-only"><fmt:message key="label.button.previous"/></span>
                            </a>
                            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                                <span class="sr-only"><fmt:message key="label.button.next"/></span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    </div>
    <div class="container">
        <c:if test="${user != null}">
            <br>
            <u:menu buttons="${menu}"/>
            <br>
        </c:if>
        <c:import url="../parts/footer.jsp"/>
    </div>
</u:html>
