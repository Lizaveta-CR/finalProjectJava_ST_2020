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

<u:html title="Add shop personal">
    <c:url value="/img/wallpaper/wallpaper-personal.jpg" var="image"/>
    <u:background image="${image}"/>

    <jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp">
        <jsp:param name="page" value="/manag/personal.html"/>
    </jsp:include>
    <c:choose>
        <c:when test="${not empty personal}">
            <c:set var="personal" value="${personal}"/>
            <div class="container">
                <h2><fmt:message key="label.employee"/></h2>
                <table class="table">
                    <thead>
                    <tr>
                        <th style="width:60%;"><fmt:message key="label.register.name"/></th>
                        <th style="width:40%;"><fmt:message key="label.register.surname"/></th>
                        <th style="width:40%;"><fmt:message key="label.register.login"/></th>
                        <th style="width:40%;"><fmt:message key="label.register.role"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="employee" items="${personal}">
                        <tr>
                            <td>${employee.name}</td>
                            <td>${employee.surname}</td>
                            <td>${employee.login}</td>
                            <td>${employee.role.identity}</td>
                            <td>
                                <form action="<c:url value="/manag/delete.html?employeeId=${employee.id}"/>"
                                      method="post">
                                    <button class="btn-info"
                                            type="submit">
                                        <fmt:message
                                                key="label.employee.delete"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="container text-center">
                <div class="col-md-11 col-md-offset-3">
                    <div class="row">
                        <div class="col-md-7 text-center">
                            <div class="thumbnail">
                                <img src="<c:url value="/img/empty.jpg"/>" alt="Empty" style="width:100%">
                                <div class="caption">
                                    <p><fmt:message key="label.manag.personal.noEmployees"/></p>
                                    <a href="<c:url value="/welcome.html"/>"><fmt:message
                                            key="label.button.back"/></a>
                                    <a href="<c:url value="/manag/add-personal.html"/>"><fmt:message
                                            key="label.employee.add"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <a href="<c:url value="/manag/add-personal.html"/>"><fmt:message
            key="label.employee.add"/></a>
    <form action="<c:url value="/welcome.html"/>" method="get">
        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                key="label.button.back"/></button>
    </form>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>