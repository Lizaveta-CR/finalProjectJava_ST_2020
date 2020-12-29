<%--
  Created by IntelliJ IDEA.
  User: elizaveta
  Date: 28.12.2020
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Music Land</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<%--<c:url value="/img/main.jpg" var="imgUrl"/>--%>
<%--<img scr='<c:out value="${imgUrl}"/>' height="200" width="300"/>--%>
<%--<img src="/Users/elizaveta/IdeaProjects/finalProjectJava_ST_2020/src/main/webapp/main.jpg">--%>
<div class="container">
    <div class="vertical-center-row">
        <div class="page-header" style="">
            <h1 class="text-center">Music Land</h1>
        </div>
        <h2 class="text-center">We are happy to meet you!</h2>
        <h3 class="text-center">Choose what you want to do:</h3>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-6 col-sm-3" style="background-color:lavender;"><a href="<c:url value="#"/>"/>Login
                </div>
                <div class="col-xs-6 col-sm-3">
                    <form method="post" action=<c:url value="welcome.jsp"/>/products}/>
                        <input type="hidden" name="command" value="categories"/>
                        <button type="submit" class="btn btn-outline-primary">Show products</button>
                        <%--                    <button type="submit">Show products</button>--%>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
