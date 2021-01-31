<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 31.01.2021
  Time: 17:11
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
<u:html title="About us" cssFile="about.css">
    <jsp:include page="../parts/nav-bar.jsp">
        <jsp:param name="page" value="/about.html"/>
    </jsp:include>
    <div class="about-section">
        <h1><fmt:message key="label.about.title"/></h1>
        <p><fmt:message key="label.welcome.main"/></p>
    </div>

    <h2 style="text-align:center"><fmt:message key="label.team.title"/></h2>
    <div class="row">
        <div class="column">
            <div class="card">
                <img src="<c:url value="/img/about/liza.jpg"/>" alt="Liza" style="width:100%">
                <div class="container">
                    <h2><fmt:message key="label.me"/></h2>
                    <p class="title">CEO & Founder</p>
                    <pre style="border: 0; background-color: transparent;"><p><fmt:message key="label.about.me"/></p></pre>
                    <p><fmt:message key="label.help.work"/>:</p>
                    <p><a href="mailto:elizaveta.tsvirko@gmail.com"><img
                            src="<c:url value="/img/mail.jpg"/>" height="20" width="20"/></a></p>
                </div>
            </div>
        </div>
    </div>
    <c:import url="../parts/footer.jsp"/>
</u:html>
