<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 21.01.2021
  Time: 12:37
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
<u:html title="Product producer info" cssFile="panel.css">
    <u:head/>
    <c:if test="${not empty producer}">
        <h3 class="text-center"><fmt:message key="label.producer.page"/> ${producer.name}</h3>
        <section id="t-cards">
            <div class="container">
                <c:forEach var="product" items="${producer.products}">
                    <div class="col-sm-6 col-md-3">
                        <div class="panel panel-default panel-card">
                            <div class="panel-heading">
                                <c:choose>
                                    <c:when test="${not empty product.imageUrl}">
                                        <c:url value="/img/${product.imageUrl}" var="image" scope="page"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/img/main.jpg" var="image" scope="page"/>
                                    </c:otherwise>
                                </c:choose>
                                <img src="${image}"/>
                                <c:choose>
                                    <c:when test="${not empty authorizedUser}">
                                        <c:choose>
                                            <c:when test="${f:isBuyer(authorizedUser) and product.available}">
                                                <c:url value="/products/buy.html" var="buyUrl"/>
                                                <td>
                                                    <form action="${buyUrl}?productId=${product.id}"
                                                          method="post">
                                                        <p><fmt:message key="label.product.amount"/></p>
                                                        <p><input type="number" min="1" value="1"
                                                                  name="amount"
                                                                  id="amount"/></p>
                                                        <button class="btn btn-primary btn-sm"
                                                                type="submit">
                                                            <fmt:message
                                                                    key="label.product.buy"/></button>
                                                    </form>
                                                </td>
                                            </c:when>
                                        </c:choose>
                                        <c:if test="${f:isAdmin(authorizedUser)}">
                                            <td>
                                                <form action="<c:url value="/products/edit.html?productId=${product.id}"/>"
                                                      method="get">
                                                    <button class="btn btn-primary btn-sm"
                                                            type="submit">
                                                        <fmt:message
                                                                key="label.product.edit"/></button>
                                                </form>
                                            </td>
                                        </c:if>
                                        <c:if test="${f:isManager(authorizedUser)}">
                                            <td>
                                                <form action="<c:url value="/products/delete.html?productId=${product.id}"/>"
                                                      method="post">
                                                    <button class="btn btn-primary btn-sm"
                                                            type="submit">
                                                        <fmt:message
                                                                key="label.employee.delete"/></button>
                                                </form>
                                            </td>
                                        </c:if>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="panel-body text-center">
                                <h4 class="panel-header">${product.model}</h4>
                                <small><fmt:formatNumber value="${product.price}" type="currency"/></small>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>
    <a href="<c:url value="/welcome.html"/>"><fmt:message
            key="label.button.back"/></a>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>
