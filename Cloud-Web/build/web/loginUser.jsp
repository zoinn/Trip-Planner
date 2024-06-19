<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="org.json.JSONTokener"%>
<%@page import="java.net.URL"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html;" 
         pageEncoding="UTF-8" %>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>Login Check</title>
</head>
<body>
<%
String username=request.getParameter("username");

URL loginURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/LoginUser?username="+username);
    
//Used to run the url and generate the new document in the db
String userData = IOUtils.toString(loginURL, "UTF-8");
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");

if (!"Username does not exist".equals(userData)){
JSONObject userDataJson = new JSONObject(new JSONTokener(userData));
Cookie cookie = new Cookie("userID", userDataJson.getString("UserID"));
response.addCookie(cookie);
    }

response.getWriter().write(userData.toString());
%>
</body>