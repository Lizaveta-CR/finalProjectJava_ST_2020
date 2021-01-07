<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 04.01.2021
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="${cookie.lang.value}">
<head>
    <title>Registration</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet" id="bootstrap-css">
    <link href="${pageContext.request.contextPath}/css/register.css" rel="stylesheet" media="all">
    <script src="${pageContext.request.contextPath}/bootstrap/jQuery/jquery-3.5.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/preventRefresh.js"></script>
</head>
<body>
<script>
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.href);
    }
</script>
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

                            <%--                        <c:forEach items="${redirectedData}" var="item" varStatus="status">--%>
                        <p class="bg-danger text-center lead"><c:out value="${message}"/></p>
                            <%--                        </c:forEach>--%>
                    </div>
                </div>
                <div style="text-align: center">
                    <a href="link">
                        <img src='<c:url value="/img/errorLogin.jpg"></c:url>' alt="error" height="100"
                             width="100"/>
                    </a>
                </div>
                </c:if>
                <form method="POST" action="${pageContext.request.contextPath}/registration"
                <%--                      onsubmit="return Validate()"--%>>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.name"/></div>
                        <div class="value">
                            <input class="input--style-6" type="text" id="name" name="name">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.surname"/></div>
                        <div class="value">
                            <input class="input--style-6" type="text" id="surname" name="surname">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.login"/></div>
                        <div class="value">
                            <input class="input--style-6" type="text" id="login" name="login">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.pass"/></div>
                        <div class="value">
                            <input class="input--style-6" type="password" id="password" name="password"
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                   title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters">
                        </div>
                    </div>
                    <%--                    <div id="message">--%>
                    <%--                        <h3>Password must contain the following:</h3>--%>
                    <%--                        <p id="letter">A <b>lowercase</b> letter</p>--%>
                    <%--                        <p id="capital">A <b>capital (uppercase)</b> letter</p>--%>
                    <%--                        <p id="number">A <b>number</b></p>--%>
                    <%--                        <p id="length">Minimum <b>8 characters</b></p>--%>
                    <%--                    </div>--%>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.submitPass"/></div>
                        <div class="value">
                            <input class="input--style-6" type="password" id="confirm_password" name="confirm_password">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.email"/></div>
                        <div class="value">
                            <div class="input-group">
                                <input class="input--style-6" type="email" name="email" placeholder="example@email.com">
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.telephone"/></div>
                        <div class="value">
                            <div class="input-group">
                                <input class="input--style-6" type="number" id="telephone" name="telephone">
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="name"><fmt:message key="label.register.balance"/></div>
                        <div class="value">
                            <div class="input-group">
                                <input class="input--style-6" type="number" id="balance" name="balance" min="10"
                                       step="any">
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
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
</div>
<%--<script>--%>
<%--    var myInput = document.getElementById("password");--%>
<%--    var letter = document.getElementById("letter");--%>
<%--    var capital = document.getElementById("capital");--%>
<%--    var number = document.getElementById("number");--%>
<%--    var length = document.getElementById("length");--%>

<%--    // When the user clicks on the password field, show the message box--%>
<%--    myInput.onfocus = function () {--%>
<%--        document.getElementById("message").style.display = "block";--%>
<%--    }--%>

<%--    // When the user clicks outside of the password field, hide the message box--%>
<%--    myInput.onblur = function () {--%>
<%--        document.getElementById("message").style.display = "none";--%>
<%--    }--%>

<%--    // When the user starts to type something inside the password field--%>
<%--    myInput.onkeyup = function () {--%>
<%--        // Validate lowercase letters--%>
<%--        var lowerCaseLetters = /[a-z]/g;--%>
<%--        if (myInput.value.match(lowerCaseLetters)) {--%>
<%--            letter.classList.remove("invalid");--%>
<%--            letter.classList.add("valid");--%>
<%--        } else {--%>
<%--            letter.classList.remove("valid");--%>
<%--            letter.classList.add("invalid");--%>
<%--        }--%>

<%--        // Validate capital letters--%>
<%--        var upperCaseLetters = /[A-Z]/g;--%>
<%--        if (myInput.value.match(upperCaseLetters)) {--%>
<%--            capital.classList.remove("invalid");--%>
<%--            capital.classList.add("valid");--%>
<%--        } else {--%>
<%--            capital.classList.remove("valid");--%>
<%--            capital.classList.add("invalid");--%>
<%--        }--%>

<%--        // Validate numbers--%>
<%--        var numbers = /[0-9]/g;--%>
<%--        if (myInput.value.match(numbers)) {--%>
<%--            number.classList.remove("invalid");--%>
<%--            number.classList.add("valid");--%>
<%--        } else {--%>
<%--            number.classList.remove("valid");--%>
<%--            number.classList.add("invalid");--%>
<%--        }--%>

<%--        // Validate length--%>
<%--        if (myInput.value.length >= 8) {--%>
<%--            length.classList.remove("invalid");--%>
<%--            length.classList.add("valid");--%>
<%--        } else {--%>
<%--            length.classList.remove("valid");--%>
<%--            length.classList.add("invalid");--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>

</body>
</html>
