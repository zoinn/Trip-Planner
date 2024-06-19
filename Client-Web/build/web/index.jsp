
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.net.URL"%>
<%@page import="org.json.JSONArray" %>
<%@page import="org.json.JSONObject" %>
<%@page import="org.json.JSONTokener" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login and Register Form</title>
    <link rel="stylesheet" type="text/css" href="siteCSS.css">
    
</head>
<body>

<%
    String errorMessage = null;

    if (request.getParameter("loginButton") != null) {
        String username = request.getParameter("username");
        URL loginURL = new URL ("http://localhost:8080/Cloud-Centric-Coursework/loginUser.jsp?username="+username);
        HttpURLConnection conLog = (HttpURLConnection) loginURL.openConnection();
        conLog.setDoOutput(true);
        conLog.setRequestMethod("GET");
        conLog.setRequestProperty("Accept", "application/json");
        conLog.setRequestProperty("Content-Type", "application/json");
        conLog.connect();

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(conLog.getInputStream()));
        String userData = inputStream.readLine();

        if ("Username does not exist".equals(userData)) {
            errorMessage = userData;
        }
        else{
            JSONObject userDataJson = new JSONObject(userData);
            String userID = userDataJson.getString("UserID");
             %>
             <script>
                var userID = '<%= userID %>';
                localStorage.setItem('userID', userID);
                setTimeout(function() {window.location.href = 'http://localhost:8080/Client-Web/home.jsp';}, 1000);
            </script>
            <%
        }
    }

    if (request.getParameter("registerButton") != null) {
        String username = request.getParameter("username");
        URL registerURL = new URL ("http://localhost:8080/Cloud-Centric-Coursework/registerDB.jsp?username="+username);
        HttpURLConnection conReg = (HttpURLConnection) registerURL.openConnection();
        conReg.setDoOutput(true);
        conReg.setRequestMethod("GET");
        conReg.setRequestProperty("Accept", "application/json");
        conReg.setRequestProperty("Content-Type", "application/json");
        conReg.connect();

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(conReg.getInputStream()));
        String userData = inputStream.readLine();

        application.log(userData);
        if ("Username Taken, Choose Another".equals(userData)) {
            errorMessage = userData;
            
        }
        else{
            JSONObject userDataJson = new JSONObject(userData);
            String userID = userDataJson.getString("UserID");
            %>
             <script>
                var userID = '<%= userID %>';
                localStorage.setItem('userID', userID);
                console.log('userID set in localStorage: ' + userID);
                setTimeout(function() {window.location.href = 'http://localhost:8080/Client-Web/home.jsp';}, 1000);
            </script>
            <%
        }
    }
    
%>

<h2>Login or Register</h2>

<form id="loginForm" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>

    <button type="submit" name="loginButton">Login</button>
    <button type="submit" name="registerButton">Register</button>
</form>

<div id="error-section">
    <%-- Display error message if there's one --%>
    <% if (errorMessage != null) { %>
        <%= errorMessage %>
    <% } %>
</div>

</body>
</html>
