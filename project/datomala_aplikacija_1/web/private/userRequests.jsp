<%-- 
    Document   : userRequest
    Created on : Jun 10, 2013, 12:40:48 PM
    Author     : dex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/default.css">
        <title>User Requests</title>
    </head>
    <body>
        <h1>User Requests</h1>
        
        <form method="POST" action="${pageContext.servletContext.contextPath}/UserRequests">
            Request:
            <input name="status" value="${request}"/></br>
            <select id="nrElementsPerPage" name="nrElementsPerPage">
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="0">All</option>
            </select>
            <input type="submit" value="Set"/>
        </form>

        <display:table name="${userRequestsData}" pagesize="${nrElementsPerPage}" class="table">
            <display:column title="Username" property="username" sortable="true" class="tableRow"/>
            <display:column title="Request" property="request" sortable="true" class="tableRow"/>
            <display:column title="Duration" property="duration" sortable="true" class="tableRow"/>
        </display:table>
        </br>
        <a href="${pageContext.servletContext.contextPath}/index.jsp">Home</a><br>
    </body>
</html>
