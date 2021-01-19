<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 04.01.2021
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page session="false" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<u:html title="Registration" cssFile="register.css">
    <jsp:include page="../parts/nav-bar.jsp"/>
    <div class="page-wrapper bg-dark p-t-100 p-b-50">
        <div class="wrapper wrapper--w900">
            <div class="card card-6">
                <div class="card-heading">
                    <h2 class="title"><fmt:message key="label.register.title"/></h2>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <c:if test="${not empty redirectedData}">
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
                    <c:url value="/registration.html" var="registrationUrl"/>
                    <form method="POST" action="${registrationUrl}" name="myForm">
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
                                <input class="input--style-6" type="password" name="password" id="password"
                                       title="password"
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
                                <input class="input--style-6" type="password" name="confirm_password"
                                       id="confirm_password"
                                       value="" onkeyup="checkPassword();"
                                       required=""
                                       oninvalid="this.setCustomValidity('<fmt:message
                                               key="label.confirm_pass.required"/>')"
                                       oninput="setCustomValidity('')">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="name"><fmt:message key="label.register.email"/></div>
                            <div class="value">
                                <div class="input-group">
                                    <input class="input--style-6" type="email" name="email" id="email"
                                           placeholder="example@email.com"
                                           value="" onkeyup="checkEmail();"
                                           required=""
                                           oninvalid="this.setCustomValidity('<fmt:message
                                                   key="label.email.required"/>')"
                                           oninput="setCustomValidity('')">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="name"><fmt:message key="label.register.telephone"/></div>
                            <div class="value">
                                <div class="input-group">
                                    <input class="input--style-6" type="number" id="telephone" name="telephone"
                                           required=""
                                           value="" onkeyup="checkPhone();"
                                           oninvalid="this.setCustomValidity('<fmt:message
                                                   key="label.telephone.required"/>')"
                                           oninput="setCustomValidity('')">
                                </div>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="name"><fmt:message key="label.register.balance"/></div>
                            <div class="value">
                                <div class="input-group">
                                    <input class="input--style-6" type="number" id="balance" name="balance" min="10"
                                           step="any" required=""
                                           oninvalid="this.setCustomValidity('<fmt:message
                                                   key="label.balance.required"/>')"
                                           oninput="setCustomValidity('')">
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button class="btn btn--radius-2 btn--blue-2" type="submit" id="submit" name="submit">
                                <fmt:message
                                        key="label.confirm"/></button>
                        </div>
                    </form>
                    <div class="card-footer">
                        <form action="<c:url value="/welcome.html"/>" method="get">
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
</u:html>
