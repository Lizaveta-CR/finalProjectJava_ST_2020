<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 12.01.2021
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<%@ page session="true" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<u:html title="Edit profile">
    <jsp:include page="/WEB-INF/jsp/parts/nav-bar.jsp">
        <jsp:param name="page" value="/buyer/editPass.html"/>
    </jsp:include>
<div class="container">
    <h1><fmt:message key="label.buyer.edit.profile"/></h1>
    <hr>
    <div class="row">
        <div class="col-md-9">
            <div class="col-md-10 col-md-offset-1">
                <c:if test="${not empty message}">
                    <c:forEach items="${message}" var="item" varStatus="status">
                        <p class="bg-danger text-center lead"><c:out value="${item}"/></p>
                    </c:forEach>
                </c:if>
            </div>
            <h3><fmt:message key="label.buyer.edit.title"/></h3>
            <form class="form-horizontal" role="form" action="<c:url value="/buyer/editPass.html"/>" method="post">
                <div class="form-group">
                    <label class="col-md-3 control-label"><fmt:message key="label.register.pass"/></label>
                    <div class="col-md-8">
                        <input class="form-control" type="password" id="password" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label"><fmt:message key="label.register.newPass"/></label>
                    <div class="col-md-8">
                        <input class="form-control" type="password" id="new_password" name="new_password"
                               required=""
                               oninvalid="this.setCustomValidity('<fmt:message key="label.pass.required"/>')"
                               oninput="setCustomValidity('')">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label"><fmt:message key="label.register.submitPass"/></label>
                    <div class="col-md-8">
                        <input class="form-control" type="password" id="confirm_password" name="confirm_password"
                               required=""
                               oninvalid="this.setCustomValidity('<fmt:message key="label.confirm_pass.required"/>')"
                               oninput="setCustomValidity('')">
                    </div>
                </div>
                <div class="form-group">
                    <div class="container-fluid">
                        <label class="col-md-3 control-label"> </label>

                        <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                                key="label.confirm"/></button>
                        <%--                        <label class="col-md-3 control-label"></label>--%>
                    </div>
                </div>
            </form>
            <form action="<c:url value="/welcome.html"/>" method="get">
                <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                        key="label.button.back"/></button>
            </form>
        </div>
    </div>
</div>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
</u:html>
