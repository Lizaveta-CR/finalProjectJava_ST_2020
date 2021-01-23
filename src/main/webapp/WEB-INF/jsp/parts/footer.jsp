<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 23.01.2021
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="i18n.messages"/>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<u:html title="Footer" cssFile="main.css">
    <div class="clear"></div>
    <div id="footer">
        <p>&copy; 2021 Tsvirko Lizaveta</p>
    </div>
</u:html>
