<%@page import="org.json.JSONArray"%>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>

<%
    try {
        String followers = "No Followers";
        if (request.getParameter("location") != null && request.getParameter("date") != null) {
            String location = request.getParameter("location");
            String date = request.getParameter("date");

            // Query trip data
            URL queryURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/queryTrip.jsp?location=" +
                    location + "&date=" + date);
            HttpURLConnection con = (HttpURLConnection) queryURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.connect();

            // Read response
            try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String locationData = inputStream.readLine();
                JSONObject locationDataJson = new JSONObject(locationData);

                // Process the data and send it back as JSON
                JSONObject result = new JSONObject();

                String localTime = locationDataJson.getJSONObject("data")
                        .getJSONArray("time_zone")
                        .getJSONObject(0)
                        .getString("localtime");

                String city = locationDataJson.getJSONObject("data")
                        .getJSONArray("request")
                        .getJSONObject(0)
                        .getString("query");

                String weatherDesc = locationDataJson.getJSONObject("data")
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getJSONArray("hourly")
                        .getJSONObject(0)
                        .getJSONArray("weatherDesc")
                        .getJSONObject(0)
                        .getString("value");

                JSONArray hourlyArray = locationDataJson.getJSONObject("data")
                        .getJSONArray("weather")
                        .getJSONObject(0)
                        .getJSONArray("hourly");

                String temperatureCelsius = hourlyArray.getJSONObject(0).getString("tempC");
                String temperatureFahrenheit = hourlyArray.getJSONObject(0).getString("tempF");
                String temperature = temperatureCelsius + " Celsius ¦¦ " + temperatureFahrenheit + " Fahrenheit";
                String forecast = weatherDesc;
                String time = localTime;

                // Query followers
                try{
                URL followersURL = new URL("http://localhost:8080/Cloud-Centric-Coursework/queryFollowersOfTrip.jsp?location=" +
                        location);
                HttpURLConnection conFollow = (HttpURLConnection) followersURL.openConnection();
                conFollow.setRequestMethod("GET");
                conFollow.setRequestProperty("Accept", "application/json");
                conFollow.connect();

                // Read followers
                BufferedReader inputStreamFollowers = new BufferedReader(new InputStreamReader(conFollow.getInputStream()));
                followers = inputStreamFollowers.readLine();
                System.out.println("location yktherules:" + followers);
                
    }
    catch(Exception e){
        followers = "No Followers";
    }
                result.put("location", city);
                result.put("date", date);
                result.put("temperature", temperature);
                result.put("forecast", forecast);
                result.put("time", localTime);
                result.put("followers", followers);

                response.setContentType("application/json");
                response.getWriter().write(result.toString());
                } catch (Exception followersException) {
                    // Handle followers exception
                    followersException.printStackTrace();
                }
           
        }
    } catch (Exception e) {
        // Handle general exception
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("Error processing the request");
    }
%>
