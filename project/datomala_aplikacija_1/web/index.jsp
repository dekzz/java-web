<%-- 
    Document   : index
    Created on : Jun 1, 2013, 5:51:31 PM
    Author     : dex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>datomala nwtis project app 1</title>
    </head>
    <body>
        <h1>app 1</h1>
        
        <ul>
            <li><a href="${pageContext.servletContext.contextPath}/Login">Login</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/MeteoData">Meteo Data</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/ServerLog">Server Log</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/UserRequests">User Requests</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/Logout">Logout</a></li>
        </ul> 
    </body>
</html>
