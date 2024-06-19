
<%@page import="org.json.JSONTokener"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Trip</title>
    </head>
    <body>
       <%
        URL generateIDURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/GenerateID");
        String generatedIDString = IOUtils.toString(generateIDURL, "UTF-8");
        JSONObject generatedID = new JSONObject(new JSONTokener(generatedIDString));
        
        String tripID = generatedID.getJSONObject("result")
                            .getJSONObject("random")
                            .getJSONArray("data")
                            .getString(0);

        
        String userID =  request.getParameter("userID");
        String location = request.getParameter("location");
        String date = request.getParameter("date");


        URL createURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/CreateTrip?tripID="+
                tripID+
                "&location="+
                location+
                "&date="+
                date+
                "&userID="+
                userID);
        
        //Used to run the url and generate the new document in the db
        String locationData = IOUtils.toString(createURL, "UTF-8");
        if (locationData.equals("Location Already Exists")){
        try{
            URL followURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/Tripfinder/FollowTrip?location="+
                location+
                "&date="+
                date+
                "&userID="+
                userID);
            String tripData = IOUtils.toString(followURL, "UTF-8");
           }
           catch(Exception e){
                e.printStackTrace();
           }
           }
        response.getWriter().write(locationData.toString());
        %>
    </body>
    
</html>
