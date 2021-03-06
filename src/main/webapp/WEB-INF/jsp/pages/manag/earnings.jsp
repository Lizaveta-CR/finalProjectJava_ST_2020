<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 17.01.2021
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<u:html title="Earnings" cssFile="earnings.css" jsFile="table-search.js">
    <jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp">
        <jsp:param name="page" value="/manag/earnings.html"/>
    </jsp:include>
    <input type="text" id="myInput" onkeyup="myFunction()" placeholder="<fmt:message key="label.orders.serach"/>"
           title="<fmt:message key="label.orders.search.title"/>">
    <table id="myTable">
        <tr class="header">
            <th style="width:60%;"><fmt:message key="label.order.buyer.orders.date"/></th>
            <th style="width:40%;"><fmt:message key="label.product.price"/></th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td><fmt:formatDate value="${order.date}" dateStyle="full"/></td>
                <td><fmt:formatNumber value="${order.price}" type="currency"/>
            </tr>
        </c:forEach>
    </table>
    <form action="<c:url value="/welcome.html"/>" method="get">
        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                key="label.button.back"/></button>
    </form>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>
