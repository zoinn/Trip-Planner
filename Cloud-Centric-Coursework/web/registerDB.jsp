<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="org.json.JSONTokener"%>
<%@page import="java.net.URL"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html;" 
         pageEncoding="UTF-8" %>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>Registration Check</title>
</head>
<body>
<%

URL generateIDURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/GenerateID");
String generatedIDString = IOUtils.toString(generateIDURL, "UTF-8");
JSONObject generatedID = new JSONObject(new JSONTokener(generatedIDString));
String username=request.getParameter("username");
String userID = generatedID.getJSONObject("result")
                    .getJSONObject("random")
                    .getJSONArray("data")
                    .getString(0);

URL registerNOSQLURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/RegisterUser?username="+
        username+
        "&userID="+
        userID);
//Used to run the url and generate the new document in the db
String registerNOSQLString = IOUtils.toString(registerNOSQLURL, "UTF-8");
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");
if (!"Username Taken, Choose Another".equals(registerNOSQLString)){
Cookie cookie = new Cookie("userID", userID);
response.addCookie(cookie);
    }
    
response.getWriter().write(registerNOSQLString.toString());

%>
<div></div>
</body>