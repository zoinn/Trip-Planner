<%-- 
    Document   : queryFollowersOfTrip
    Created on : 6 Jan 2024, 03:15:51
    Author     : Alishah
--%>

<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.net.URL" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Query Followers Of Trip</title>
</head>
<body>
    <%
        try {
            String location = request.getParameter("location");
            URL followersURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/QueryFollowersOfTrip?location=" + location);
            HttpURLConnection conFollow = (HttpURLConnection) followersURL.openConnection();

            // Set up the connection
            conFollow.setDoOutput(true);
            conFollow.setRequestMethod("GET");
            conFollow.setRequestProperty("Accept", "application/json");
            conFollow.setRequestProperty("Content-Type", "application/json");

            // Connect to the URL
            conFollow.connect();

            // Read the response
            try (BufferedReader inputStreamFollowers = new BufferedReader(new InputStreamReader(conFollow.getInputStream()))) {
                String followers = inputStreamFollowers.readLine();
                application.log(followers);

                // Set response content type and write the followers to the response
                response.setContentType("application/json");
                response.getWriter().write(followers);
            }
        } catch (Exception e) {
            // Handle exceptions (log or display an error message)
            application.log("Error in querying followers: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in querying followers.");
        }
    %>
</body>
</html>
