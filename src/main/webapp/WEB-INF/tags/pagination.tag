<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="action" required="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

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
                        <form action="<c:url value="${action}?page=${i}"/>" method="post">
                            <button class="btn btn-primary" type="submit">${i}</button>
                        </form>
                    </li>
                </div>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</ul>