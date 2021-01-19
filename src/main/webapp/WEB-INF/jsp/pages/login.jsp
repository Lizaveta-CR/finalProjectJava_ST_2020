<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 03.01.2021
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="f" uri="function" %>
<%@ page session="false" %>
<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<c:set var="cssFiles" value="${['login.css','panel.css']}" scope="page"/>
<u:html title="Login" cssFiles="${cssFiles}">
    <script>
        if (window.history.replaceState) {
            window.history.replaceState(null, null, window.location.href);
        }
    </script>
    <div class="sidenav">
        <div class="login-main-text">
            <h2><fmt:message key="label.login.title"/></h2>
            <p><fmt:message key="label.login.title.second"/></p>
        </div>
    </div>
    <div class="main">
        <div class="row">
            <div class="col-md-4">
                <c:url value="/registration.html" var="registrationUrl"/>
                <form action="${registrationUrl}" method="get">
                    <button type="submit" class="btn btn-secondary"><fmt:message key="label.register.title"/></button>
                </form>
                <c:url value="/welcome.html" var="welcomeUrl"/>
                <form action="${welcomeUrl}" method="get">
                    <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                            key="label.button.back"/></button>
                </form>
            </div>
        </div>
        <div class="col-md-6 col-sm-12">
            <div class="login-form">
                <c:if test="${not empty message or not empty redirectedData}">
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <p class="bg-danger text-center lead"><c:out value="${message}"/></p>
                            <p class="bg-danger text-center lead"><c:out value="${redirectedData}"/></p>
                        </div>
                    </div>
                    <div style="text-align: center">
                        <a href="link">
                            <img src='<c:url value="/img/errorLogin.jpg"></c:url>' alt="error" height="100"
                                 width="100"/>
                        </a>
                    </div>
                </c:if>
                <c:url value="/login.html" var="loginUrl"/>
                <form action="${loginUrl}" method="post">
                    <div class="form-group" act>
                        <label><fmt:message key="label.register.login"/></label>
                        <input type="text" class="form-control" placeholder=
                            <fmt:message key="label.register.login"/> id="login" name="login" required=""
                               oninvalid="this.setCustomValidity('<fmt:message key="label.login.required"/>')"
                               oninput="setCustomValidity('')">
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="label.register.pass"/></label>
                        <input type="password" class="form-control" placeholder=
                            <fmt:message key="label.register.pass"/> id="password" name="password" required=""
                               oninvalid="this.setCustomValidity('<fmt:message key="label.pass.required"/>')"
                               oninput="setCustomValidity('')">
                    </div>
                    <button type="submit" class="btn btn-black"><fmt:message key="label.title.login"/></button>
                </form>
            </div>
        </div>
    </div>
</u:html>
