<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 14.01.2021
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="function" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<c:choose>
    <c:when test="${not empty sessionScope.authorizedUser}">
        <c:set var="user" value="${sessionScope.authorizedUser}"/>
    </c:when>
</c:choose>
<u:html title="Products">
    <%@include file="/WEB-INF/jsp/parts/nav-bar.jsp" %>
    <c:choose>
        <c:when test="${not empty products}">
            <table class="table">
                <tr>
                    <th scope="col"><fmt:message
                            key="label.product.model"/></th>
                    <th scope="col"><fmt:message
                            key="label.producer.name"/></th>
                    <th scope="col"><fmt:message
                            key="label.product.price"/></th>
                    <th scope="col"><fmt:message
                            key="label.product.access"/></th>
                </tr>
                <c:forEach var="product" items="${products}">
                    <tbody>
                    <tr>
                        <td> ${product.model}</td>
                        <td> ${product.producer.name}</td>
                        <td> ${product.price}</td>
                        <td>
                            <form action="<c:url value="/admin/unavailableProducts.html?productId=${product.id}"/>"
                                  method="post">
                                <div class="custom-control custom-checkbox">
                                    <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                            key="label.button.enable"/></button>
                                </div>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <div class="container text-center">
                <div class="col-md-11 col-md-offset-3">
                    <div class="row">
                        <div class="col-md-7 text-center">
                            <div class="thumbnail">
                                <img src="<c:url value="/img/empty.jpg"/>" alt="Empty" style="width:100%">
                                <div class="caption">
                                    <p><fmt:message key="label.amin.products.allAvail"/></p>
                                    <a href="<c:url value="/welcome.html"/>"><fmt:message
                                            key="label.button.back"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</u:html>
