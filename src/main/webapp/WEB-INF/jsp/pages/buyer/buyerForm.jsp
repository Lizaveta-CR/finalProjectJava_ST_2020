<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 05.01.2021
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page session="true" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<u:html title="Buyer" jsFile="main.js">
    <jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp">
        <jsp:param name="page" value="/buyer/buyerForm.html"/>
    </jsp:include>
    <c:set var="user" scope="session" value="${sessionScope.authorizedUser}"/>
    <c:set var="buyer" scope="session" value="${sessionScope.authorizedBuyer}"/>

    <div class="container" style="background-image:  url('<c:url value="/img/back.jpg"/>');">
        <h2 class="text-center"><ctg:welcome name="${user.name}"/></h2>
        <div class="col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
            <div class="col-sm-12">
                <div class="col-xs-12 col-sm-8">
                    <br>
                    <p><strong><fmt:message key="label.register.email"/>: </strong> <c:out
                            value="${buyer.email}"/>
                    </p>
                    <p><strong><fmt:message key="label.register.telephone"/>: </strong> <c:out
                            value="${buyer.telephone}"/></p>
                    <p><strong><fmt:message key="label.register.balance"/>: </strong>
                        <fmt:formatNumber value="${buyer.balance}" type="currency"/>
                    </p>
                    <p><strong><fmt:message key="label.register.bonus"/>: </strong>
                        <fmt:formatNumber value="${buyer.bonus}" type="currency"/>
                    </p>
                </div>
            </div>
            <div class="col-xs-12 divider text-center">
                <div class="col-xs-12 col-sm-4 emphasis">
                    <form action="<c:url value="/buyer/edit.html"/>" method="get">
                        <button class="btn btn-info btn-block" type="submit"><fmt:message
                                key="label.buyer.edit"/></button>
                    </form>
                </div>
                <div class="col-xs-12 col-sm-4 emphasis">
                    <form action="<c:url value="/buyer/address.html"/>" method="get">
                        <button class="btn btn-info btn-block" type="submit"><fmt:message
                                key="label.buyer.address"/></button>
                    </form>
                </div>
                <div class="col-xs-12 col-sm-4 emphasis">
                    <form action="<c:url value="/buyer/order.html"/>" method="get">
                        <button class="btn btn-info btn-block" type="submit"><fmt:message
                                key="label.buyer.button.order"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <h2><fmt:message key="label.order.buyer.orders"/></h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="label.order.buyer.orders.date"/></th>
                <th><fmt:message key="label.product.price"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><fmt:formatDate value="${order.date}" dateStyle="full"/></td>
                    <td><fmt:formatNumber value="${order.price}" type="currency"/>
                        <a href="<c:url value="/buyer/feedBack.html?order=${order.id}"/>"><fmt:message
                                key="label.order.leave.feedback"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <ul class="pagination">
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <div class="btn-group">
                            <li>
                                <button class="btn btn-primary" type="submit">${i}</button>
                            </li>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="btn-group">
                            <li>
                                <form action="<c:url value="/buyer/buyerForm.html?page=${i}"/>" method="post">
                                    <button class="btn btn-primary" type="submit">${i}</button>
                                </form>
                            </li>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>
