<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 18.01.2021
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="function" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<html>
<head>
    <title>Create product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/fonts/style.css">
    <%--    <link rel="stylesheet"--%>
    <%--          href="${pageContext.request.contextPath}/bootstrap/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">--%>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/product.css">
    <u:head/>
</head>
<body>

<div class="wrapper">
    <div class="inner">
        <form action="">
            <h3><fmt:message key="label.product.add"/></h3>
            <div class="form-row">
                <div class="form-wrapper">
                    <label for="">Name *</label>
                    <input type="text" class="form-control" placeholder="Your Name">
                </div>
                <div class="form-wrapper">
                    <label for="">Phone *</label>
                    <input type="text" class="form-control" placeholder="Phone">
                </div>
            </div>
            <div class="form-row">
                <div class="form-wrapper">
                    <label for="">Check-in *</label>
                    <span class="lnr lnr-calendar-full"></span>
                    <input type="text" class="form-control datepicker-here" data-language='en'
                           data-date-format="dd M yyyy" id="dp1">
                </div>
                <div class="form-wrapper">
                    <label for="">Check-out *</label>
                    <span class="lnr lnr-calendar-full"></span>
                    <input type="text" class="form-control datepicker-here" data-language='en'
                           data-date-format="dd M yyyy" id="dp2">
                </div>
            </div>
            <div class="form-row last">
                <div class="form-wrapper">
                    <label for="">Adults *</label>
                    <select name="" id="" class="form-control">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                    </select>
                    <i class="zmdi zmdi-chevron-down"></i>
                </div>
                <div class="form-wrapper">
                    <label for="">Chidren *</label>
                    <%--                    TODO--%>
                    <select name="" id="" class="form-control">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                    </select>
                    <i class="zmdi zmdi-chevron-down"></i>
                </div>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox"> No one rejects, dislikes, or avoids pleasure itself.
                    <span class="checkmark"></span>
                </label>
            </div>
            <button data-text="Book Room">
                <span>Book Room</span>
            </button>
        </form>
    </div>
</div>


<%--<!-- DATE-PICKER -->--%>
<%--<script src="vendor/date-picker/js/datepicker.js"></script>--%>
<%--<script src="vendor/date-picker/js/datepicker.en.js"></script>--%>

<%--<script src="js/main.js"></script>--%>
</body><!-- This templates was made by Colorlib (https://colorlib.com) -->
</body>
</html>
