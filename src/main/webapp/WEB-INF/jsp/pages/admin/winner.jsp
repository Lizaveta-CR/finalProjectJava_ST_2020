<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 15.01.2021
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="f" uri="function" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:setBundle basename="i18n.messages"/>
<u:html title="Buyers" cssFile="loading.css" jsFile="main.js">
    <u:head/>
    <body onload="load()" style="margin:0; background: url(/img/wallpaper/wallpaper-winner.jpg);">
    <div id="loader"></div>
    <div style="display:none;" id="myDiv" class="animate-bottom">
        <c:if test="${not empty buyer}">
            <div class="container text-center">
                <div class="page-header">
                    <h1><fmt:message key="label.admin.buyer.winner"/></h1>
                </div>
                <div class="col-lg-4 text-center">
                    <div class="text-center card-box text-center">
                        <div class="member-card pt-2 pb-2">
                            <div class="thumb-lg member-thumb mx-auto">
                                <img src="<c:url value="/img/profile.jpg"/>"
                                     class="rounded-circle img-thumbnail" alt="profile-image" height="200" width="200">
                            </div>
                            <div class="">
                                <h4>${buyer.email}</h4>
                                <p class="text-muted"><span>| </span>
                                <form action="<c:url value="/admin/mail.html?buyerId=${buyer.id}"/>" method="POST">
                                    <table>
                                        <tr>
                                            <td><fmt:message key="label.register.bonus"/>:</td>
                                            <td><input type="number" name="bonus"
                                                       placeholder="<fmt:message key="label.bonus.required"/>" required
                                                       oninvalid="this.setCustomValidity('<fmt:message
                                                               key="label.bonus.required"/>')"
                                                       oninput="setCustomValidity('')"/></td>
                                        </tr>
                                        <tr>
                                            <td><fmt:message key="label.admin.mail.sendTo"/>:</td>
                                            <td><input type=" text" name="to" value="${buyer.email}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><fmt:message key="label.admin.mail.subject"/>:</td>
                                            <td><input type="text" name="subject"
                                                       value="<fmt:message key="label.admin.mail.subject.value"/>"/>
                                            </td>
                                        </tr>
                                    </table>
                                    <hr/>
                                    <textarea type="text" name="body" rows="5" cols="45"><fmt:message
                                            key="label.admin.mail.text"/></textarea>
                                    <br/><br/>
                                    <input type="submit" value="<fmt:message
                                    key="label.admin.mail.send"/>">
                                </form>
                                </p>
                            </div>
                            </p>
                        </div>

                        <div class="mt-4">
                            <div class="row">
                                <div class="col-4">
                                    <div class="mt-3">
                                        <p><fmt:message key="label.admin.buyer.access"/></p>
                                        <c:set var="enabled" scope="request" value="${buyer.enabled}"/>
                                        <c:choose>
                                            <c:when test="${enabled}">
                                                <h4><fmt:message key="label.admin.buyer.have"/></h4>
                                            </c:when>
                                            <c:otherwise>
                                                <h4><fmt:message key="label.admin.buyer.no"/></h4>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="mt-3">
                                        <h4>${fn:length(buyer.orders)}</h4>
                                        <p><fmt:message
                                                key="label.admin.buyer.total.orders"/></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <form action="<c:url value="/welcome.html"/>" method="get">
            <button class="btn btn--radius-2 btn--blue-2" type="submit"><fmt:message
                    key="label.button.back"/></button>
        </form>
    </div>
    </div>
    <c:import url="/WEB-INF/jsp/parts/footer.jsp"/>
    </body>
</u:html>
