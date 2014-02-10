<%-- 
    Document   : login
    Created on : Apr 16, 2013, 5:07:56 PM
    Author     : dex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NWTiS app1 - login</title>
    </head>
    <body>
        <h1>NWTiS app1 - login</h1>
        <form method="POST" action="${pageContext.servletContext.contextPath}/UserCheck">
            Username: </br>
            <input name="username"/> </br>
            Password: </br>
            <input name="password" type="password"/> </br>
            <input type="submit" value="Login"/>
        </form>
    </body>
</html>
