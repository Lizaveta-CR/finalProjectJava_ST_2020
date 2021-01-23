<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 17.01.2021
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page session="false" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<u:html title="Add shop personal" cssFile="register.css" jsFile="prevent-refresh.js">
    <jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp">
        <jsp:param name="page" value="/manag/add-personal.html"/>
    </jsp:include>
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
                    <form method="POST" action="<c:url value="/manag/add-personal.html"/>">
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
                            <div class="name"><fmt:message key="label.register.role"/></div>
                            <div class="col-lg-8">
                                <select id="role" name="role">
                                    <option value="0"><fmt:message key="label.admin"/></option>
                                    <option value="1"><fmt:message key="label.manager"/></option>
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
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>