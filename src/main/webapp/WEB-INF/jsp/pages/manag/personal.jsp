<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 17.01.2021
  Time: 17:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page session="false" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="${cookie.lang.value}">
<head>
    <title>Add shop personal</title>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/register.css" rel="stylesheet" media="all">
    <u:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp"/>
<div class="page-wrapper bg-dark p-t-100 p-b-50">
    <div class="wrapper wrapper--w900">
        <div class="card card-6">
            <div class="card-heading">
                <h2 class="title"><fmt:message key="label.register.title"/></h2>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <c:if test="${not empty message}">
                        <p class="bg-danger text-center lead"><c:out value="${message}"/></p>
                    </div>
                </div>
                <div style="text-align: center">
                    <a href="link">
                        <img src='<c:url value="/img/errorLogin.jpg"></c:url>' alt="error" height="100" width="100"/>
                    </a>
                </div>
                </c:if>
                <form method="POST" action="${pageContext.request.contextPath}/manag/personal" name="myForm">
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.name"/></div>
                        <div class="value">
                            <input class="input--style-6" type="text" id="name" name="name" required=""
                                   value="" onkeyup="checkName();"
                                   oninvalid="this.setCustomValidity('<fmt:message key="label.name.required"/>')"
                                   oninput="setCustomValidity('')">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.surname"/></div>
                        <div class="value">
                            <input class="input--style-6" type="text" id="surname" name="surname" required=""
                                   value="" onkeyup="checkName();"
                                   oninvalid="this.setCustomValidity('<fmt:message key="label.surname.required"/>')"
                                   oninput="setCustomValidity('')">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.login"/></div>
                        <div class="value">
                            <input class="input--style-6" type="text" id="login" name="login" required=""
                                   value="" onkeyup="checkLogin();"
                                   oninvalid="this.setCustomValidity('<fmt:message key="label.login.required"/>')"
                                   oninput="setCustomValidity('')">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.pass"/></div>
                        <div class="value">
                            <input class="input--style-6" type="password" name="password" id="password" title="password"
                                   value="" onkeyup="checkPassword();"
                                   required=""
                                   oninvalid="this.setCustomValidity('<fmt:message key="label.pass.required"/>')"
                                   oninput="setCustomValidity('')"
                            >
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.submitPass"/></div>
                        <div class="value">
                            <input class="input--style-6" type="password" name="confirm_password" id="confirm_password"
                                   value="" onkeyup="checkPassword();"
                                   required=""
                                   oninvalid="this.setCustomValidity('<fmt:message
                                           key="label.confirm_pass.required"/>')"
                                   oninput="setCustomValidity('')">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.role"/></div>
                        <div class="col-lg-8">
                            <select id="role" name="role">
                                <option value="admin">Admin</option>
                                <option value="manager">Manager</option>
                            </select>
                        </div>
                    </div>

                    <div class="card-footer">
                        <button class="btn btn--radius-2 btn--blue-2" type="submit" id="submit" name="submit">
                            <fmt:message
                                    key="label.confirm"/></button>
                    </div>
                </form>
                <div class="card-footer">
                    <form action="<c:url value="/welcome"/>" method="get">
                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                key="label.button.back"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        <jsp:directive.include file="/js/validator-register.js"/>
    </script>
</div>
<script>
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.href);
    }
</script>
</body>
</html>