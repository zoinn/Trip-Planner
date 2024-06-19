<%-- 
    Document   : followTrip
    Created on : 6 Jan 2024, 03:15:19
    Author     : Alishah
--%>

<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Follow Trip</title>
    </head>
    <body>
        <%
            
        String userID =  request.getParameter("userID");
        String date = request.getParameter("date");
        String location = request.getParameter("location");
        
        URL followURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/FollowTrip?location="+
                location+
                "&date="+
                date+
                "&userID="+
                userID);
        String tripData = IOUtils.toString(followURL, "UTF-8");
        if(tripData.equals("Location Not Found")){
              URL createURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/createTrip.jsp?location="+
                location+
                "&date="+
                date+
                "&userID="+
                userID);
            String locationData = IOUtils.toString(createURL, "UTF-8");
            }
        
        %>
    </body>
</html>
