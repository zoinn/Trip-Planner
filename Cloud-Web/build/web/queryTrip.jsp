<%-- 
    Document   : queryTrip
    Created on : 6 Jan 2024, 03:15:35
    Author     : Alishah
--%>

<%@page import="org.json.JSONTokener"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Query Trip</title>
    </head>
    <body>
        <%
            String location = request.getParameter("location");
            String date = request.getParameter("date");
            URL url = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/QueryTrip?location="+
                                                                                        location+"&date="+date);
            String locationData = IOUtils.toString(url, "UTF-8");
            response.getWriter().write(locationData.toString());
        %>
    </body>
</html>
