<%-- 
    Document   : meteoData
    Created on : Jun 10, 2013, 12:39:58 PM
    Author     : dex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/default.css">
        <title>Meteo Data</title>
    </head>
    <body>
        <h1>Meteo Data</h1>
        
        <form method="POST" action="${pageContext.servletContext.contextPath}/MeteoData">
            ZipCode: 
            <input name="zipcode" value="${zipcode}"/></br>
            Date </br>
            From:
            <input name="fromDate" value="${fromDate}"/>
            To:
            <input name="toDate" value="${toDate}"/></br>
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
            
        <display:table name="${meteoData}" pagesize="${nrElementsPerPage}" class="table">
            <display:column title="ZipCode" property="zipcode" sortable="true" class="tableRow"/>
            <display:column title="City" property="city" sortable="true" class="tableRow"/>
            <display:column title="Temperature" property="temperature" sortable="true" class="tableRow"/>
            <display:column title="Latitude" property="latitude" sortable="true" class="tableRow"/>
            <display:column title="Longitude" property="longitude" sortable="true" class="tableRow"/>
            <display:column title="Date" property="dateadd" sortable="true" class="tableRow"/>
        </display:table>
        </br>
        <a href="${pageContext.servletContext.contextPath}/index.jsp">Home</a><br>
    </body>
</html>
