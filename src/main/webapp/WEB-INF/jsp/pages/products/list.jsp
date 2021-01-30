<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 29.12.2020
  Time: 20:31
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
<u:html title="Categories">
    <c:url value="/img/wallpaper/wallpaper.jpg" var="image"  scope="session"/>
    <u:background image="${image}"/>

    <jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp">
        <jsp:param name="page" value="/products/list.html"/>
    </jsp:include>
    <c:if test="${not empty redirectedData}">
        <script type="text/javascript">
            var message = '<c:out value="${redirectedData}"/>';
            alert(message);
        </script>
    </c:if>
    <c:forEach items="${category.components}" var="parentItem">
        <div class="container">
            <div class="panel-group">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <c:set var="collapseVar" value="${parentItem.name}"/>
                            <a data-toggle="collapse" href="#${collapseVar}">${collapseVar}</a>
                        </h4>
                    </div>
                    <div id="${collapseVar}" class="panel-collapse collapse">
                        <c:forEach items="${parentItem.components}" var="childItem">
                            <div class="panel-body">
                                <table class="table">
                                    <caption>${childItem.name}</caption>
                                    <thead>
                                    <tr>
                                        <th scope="col"><fmt:message
                                                key="label.product.picture"/></th>
                                        <th scope="col"><fmt:message
                                                key="label.product.model"/></th>
                                        <th scope="col"><fmt:message
                                                key="label.product.description"/></th>
                                        <th scope="col"><fmt:message
                                                key="label.address.country"/></th>
                                        <th scope="col"><fmt:message
                                                key="label.producer.name"/></th>
                                        <th scope="col"><fmt:message
                                                key="label.product.price"/></th>
                                        <th scope="col"><fmt:message
                                                key="label.product.rate"/></th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="product" items="${childItem.products}">
                                        <c:if test="${product.available}">
                                            <c:choose>
                                                <c:when test="${not empty product.imageUrl}">
                                                    <c:url value="/img/${product.imageUrl}" var="image"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:url value="/img/main.jpg" var="image"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <tbody>
                                            <tr>
                                                <td>
                                                    <a href="<c:url value="/products/producer.html?producerId=${product.producer.id}"/>">
                                                        <img src="${image}" class="img-thumbnail" height="200"
                                                             width="300"/></a>
                                                </td>
                                                <td> ${product.model}</td>
                                                <td> ${product.description}</td>
                                                <td> ${product.producer.country.name}</td>
                                                <td> ${product.producer.name}</td>
                                                <td><fmt:formatNumber value="${product.price}"
                                                                      type="currency"/></td>
                                                <c:choose>
                                                    <c:when test="${not empty rateMap[product.id]}">
                                                        <c:url value=" ${rateMap[product.id]}" var="mark"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="mark" scope="page" value="-"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td> ${mark}</td>
                                                <c:choose>
                                                    <c:when test="${not empty user}">
                                                        <c:choose>
                                                            <c:when test="${f:isBuyer(user)}">
                                                                <c:url value="/products/buy.html" var="buyUrl"/>
                                                                <td>
                                                                    <form action="${buyUrl}?productId=${product.id}"
                                                                          method="post">
                                                                        <p><fmt:message
                                                                                key="label.product.amount"/></p>
                                                                        <p><input type="number" min="1" value="1"
                                                                                  name="amount"
                                                                                  id="amount"/></p>
                                                                        <button class="btn btn-info btn-block"
                                                                                type="submit">
                                                                            <fmt:message
                                                                                    key="label.product.buy"/></button>
                                                                    </form>
                                                                </td>
                                                            </c:when>
                                                            <c:when test="${f:isAdmin(user)}">
                                                                <td>
                                                                    <a href="<c:url value="/products/edit.html?productId=${product.id}"/>"><fmt:message
                                                                            key="label.product.edit"/></a></td>
                                                                </td>
                                                            </c:when>
                                                            <c:when test="${f:isManager(user)}">
                                                                <td>
                                                                    <form action="<c:url value="/products/delete.html?productId=${product.id}"/>"
                                                                          method="post">
                                                                        <button class="btn-info"
                                                                                type="submit">
                                                                            <fmt:message
                                                                                    key="label.employee.delete"/></button>
                                                                    </form>
                                                                </td>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                </c:choose>
                                            </tr>
                                            </tbody>
                                        </c:if>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:choose>
        <c:when test="${not empty user}">
            <c:choose>
                <c:when test="${f:isManager(user)}">
                    <form action="<c:url value="/products/create.html"/>" method="get">
                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                key="label.product.add"/></button>
                    </form>
                </c:when>
                <c:when test="${f:isBuyer(user)}">
                    <form action="<c:url value="/products/byRate.html"/>" method="post">
                        <p><input type="number" min="0" value="1" max="10" name="mark" id="mark"
                                  placeholder="10"  title="<fmt:message key="app.message.mark.incorrect"/>"/></p>
                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                key="label.button.find_by_mark"/></button>
                    </form>
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>
