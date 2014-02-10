<%-- 
    Document   : index
    Created on : May 2, 2013, 8:21:21 AM
    Author     : dex
--%>

<%@page import="org.foi.nwtis.datomala.ws.clients.WeatherBugClient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>datomala zadaca 3_1</title>
    </head>
    <body>
        <h1>datomala zadaca 3_1</h1>
        <%
            String zip = request.getParameter("zip");
            org.foi.nwtis.datomala.ws.clients.WeatherBugClient wbc = new org.foi.nwtis.datomala.ws.clients.WeatherBugClient();
            net.wxbug.api.LiveWeatherData lwd = null;
        
            if(zip == null || zip.length() == 0) {
        %>
        <b>Zip value not entered!</b>
        <%
            } else {
                lwd = wbc.getMeteoData(zip);
            }
            request.setAttribute("wb", lwd);
        %>
        <form method="GET">
            Zip code: <input value="" name="zip"/><br/>
            <input type="submit" value="Get Meteo Data"/>
            <table border="1">
                <tr><td>Grad: </td><td>${wb.city}</td></tr>
                <tr><td>Sirina: </td><td>${wb.longitude}</td></tr>
                <tr><td>Visina: </td><td>${wb.latitude}</td></tr>
                <tr><td>Temp: </td><td>${wb.temperature}</td></tr>
                <tr><td>Vlaga: </td><td>${wb.humidity}</td></tr>
            </table>
        </form>
    </body>
</html>
