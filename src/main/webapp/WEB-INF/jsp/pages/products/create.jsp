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
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/product.css">
    <u:head/>
</head>
<body>
<%--JS validation--%>
<div class="wrapper">
    <div class="inner">
        <div class="col-md-10 col-md-offset-1">
            <c:if test="${not empty redirectedData}">
                <c:forEach items="${redirectedData}" var="item" varStatus="status">
                    <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
                </c:forEach>
            </c:if>
        </div>
        <form action="<c:url value="/products/create"/>" method="post"  enctype="multipart/form-data">
            <h3><fmt:message key="label.product.add"/></h3>
            <div class="form-row">
                <div class="form-wrapper">
                    <label for="category"><fmt:message key="label.product.category"/></label>
                    <select name="category" id="category" class="form-control">
                        <c:forEach items="${category.components}" var="child">
                            <c:forEach items="${child.components}" var="childComp">
                                <option value="${childComp.id}">
                                    <c:out value=" ${childComp.name}"/>
                                </option>
                            </c:forEach>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-wrapper">
                    <label for="model"> <fmt:message key="label.product.model"/></label>
                    <input type="text" class="form-control" placeholder=
                            "<fmt:message key="label.product.model"/>" id="model" name="model">
                </div>
            </div>
            <div class="form-row">
                <div class="form-wrapper">
                    <label for="file"><fmt:message key="label.product.description"/></label>
                    <input type="button" id="upload" value="<fmt:message key="label.file.upload"/>"
                           onclick="document.getElementById('file').click();"/>
                    <input type="file" style="display:none;" id="file" name="file"/>
                </div>
                <%--                <div class="form-wrapper">--%>
                <%--                    <label for="imgFile"><fmt:message key="label.product.picture"/></label>--%>
                <%--                    <input type="button" id="uploadImg" value="<fmt:message key="label.file.upload"/>"--%>
                <%--                           onclick="document.getElementById('imgFile').click();"/>--%>
                <%--                    <input type="file" style="display:none;" id="imgFile" name="imgFile"/>--%>
                <%--                </div>--%>
                <div class="form-wrapper">
                    <label for="price"><fmt:message key="label.product.price"/></label>
                    <input type="number" id="price" name="price" min="10"
                           step="any">
                </div>
            </div>
            <div class="form-row last">
                <div class="form-wrapper">
                    <label for="producer"><fmt:message key="label.producer.name"/></label>
                    <select name="producer" id="producer" class="form-control">
                        <c:forEach items="${producers}" var="producer">
                            <option value="${producer.id}">
                                <c:out value=" ${producer.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <a href="#producers" class="btn btn-info" data-toggle="collapse"><fmt:message
                            key="label.producer.add"/></a>
                    <div id="producers" class="collapse">
                        <div class="form-row">
                            <div class="form-wrapper">
                                <label for="name"><fmt:message key="label.producer.name"/></label>
                                <input type="text" id="name" name="name">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-wrapper">
                                <label for="country"><fmt:message key="label.address.country"/></label>
                                <select id="country" name="country" class="form-control">
                                    <c:forEach items="${countries}" var="country">
                                        <option value="${country}">
                                            <c:out value=" ${country}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <i class="zmdi zmdi-chevron-down"></i>
                </div>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="access" name="access"><fmt:message key="lanel.product.add.checkbox"/>
                    <span class="checkmark"></span>
                </label>
            </div>
            <button data-text="<fmt:message key="label.confirm"/>" type="submit">
                <span>"<fmt:message key="label.confirm"/></span>
            </button>
        </form>
        <form action="<c:url value="/welcome"/>" method="get">
            <button type="submit" data-text=<fmt:message key="label.button.back"/>>
                <span><fmt:message key="label.button.back"/></span>
            </button>
        </form>
    </div>
</div>
</body>
</body>
</html>
