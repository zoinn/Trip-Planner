/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package CreateTrip;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.console;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * REST Web Service
 *
 * @author Alishah
 */
@Path("CreateTrip")
public class CreateTrip {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CreateTripResource
     */
    public CreateTrip() {
    }

    /**
     * Retrieves representation of an instance of CreateTrip.CreateTrip
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam ("tripID") String tripID,
                          @QueryParam ("location") String location,
                          @QueryParam ("userID") String userID, 
                          @QueryParam ("date") String date){
     try {
            String request = String.format("{\"TripID\":\"%s\",\"LocationData\":%s,\"UsersFollowing\":\"%s\"}",
                    tripID, (getLocationData(location, date)), userID);

            URL url = new URL("https://tripfinder-2108.restdb.io/rest/tripfindertrips");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-apikey", "62c3bc94436c0a56b1f58f6ffd0a08550d95e");
            con.setRequestProperty("cache-control", "no-cache");

            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(request);
            osw.flush();
            osw.close();
            os.close();
            con.connect();

            BufferedReader response = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String lineString = response.readLine();

            updateUserFollowing(userID, tripID);

            return lineString;
        } catch (JSONException e) {
            return "No followers found";
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Exception: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
   
    }
    
    public JSONObject getLocationData(String location, String date) throws MalformedURLException, IOException{
        
        URL url = new URL("https://api.worldweatheronline.com/premium/v1/weather.ashx?key=a42d7911f24645fe86b234319240601&q="+location+"&format=json&num_of_days=1&extra=no&date="+date+"&fx=yes&cc=no&mca=no&fx24=no&includelocation=no&tp=24&showlocaltime=yes&alerts=no&aqi=no");
        String locationData = IOUtils.toString(url, "UTF-8");
        JSONObject locationDataJSON = new JSONObject(new JSONTokener(locationData));
  
        return locationDataJSON;
    }
    public JSONObject findUserByID(String userID) throws MalformedURLException, IOException {
    URL getURL = new URL("https://tripfinder-2108.restdb.io/rest/tripfinderusers");
    HttpURLConnection conGet = (HttpURLConnection) getURL.openConnection();
    conGet.setRequestMethod("GET");
    conGet.setRequestProperty("Accept", "application/json");
    conGet.setRequestProperty("Content-Type", "application/json");
    conGet.setRequestProperty("x-apikey", "62c3bc94436c0a56b1f58f6ffd0a08550d95e");
    conGet.setRequestProperty("cache-control", "no-cache");

    conGet.connect();

    try (BufferedReader responseGet = new BufferedReader(new InputStreamReader(conGet.getInputStream()))) {
        StringBuilder jsonResponse = new StringBuilder();
        String line;
        while ((line = responseGet.readLine()) != null) {
            jsonResponse.append(line);
        }

        // Parse the JSON array
        JSONArray jsonArray = new JSONArray(jsonResponse.toString());

        // Iterate through each document in the array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject document = jsonArray.getJSONObject(i);
            if (userID.equals(document.getString("UserID"))) {
                return document;
            }
        }
    } catch (IOException e) {
        // Handle the exception appropriately (log or throw a more specific exception)
        e.printStackTrace();
    }

    // Return null or an empty string if the user is not found
    return null;
}

    public JSONObject findUserByName(String user) throws MalformedURLException, IOException{

        URL getURL = new URL("https://tripfinder-2108.restdb.io/rest/tripfinderusers");
        HttpURLConnection conGet = (HttpURLConnection) getURL.openConnection();
        conGet.setRequestMethod("GET");
        conGet.setRequestProperty("Accept", "application/json");
        conGet.setRequestProperty("Content-Type", "application/json");
        conGet.setRequestProperty("x-apikey", "62c3bc94436c0a56b1f58f6ffd0a08550d95e");
        conGet.setRequestProperty("cache-control", "no-cache");

        conGet.connect();

        BufferedReader responseGet = new BufferedReader(new InputStreamReader(conGet.getInputStream()));

        StringBuilder jsonResponse = new StringBuilder();
        String line;
        while ((line = responseGet.readLine()) != null) {
            jsonResponse.append(line);
        }

        // Parse the JSON array
        JSONArray jsonArray = new JSONArray(jsonResponse.toString());

        // Iterate through each document in the array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject document = jsonArray.getJSONObject(i);
            if (user.equals(document.getString("Username"))) {
                return document;
            }
        }
        return new JSONObject ("Username does not exist");
    }
    
    public String updateUserFollowing(String userID, String tripID) throws MalformedURLException, IOException{
    
    var userData = findUserByID(userID);
    //System.out.println("User Data: " + userData);

    String request = String.format("{\"UserID\":\"%s\",\"Username\":\"%s\",\"FollowedTrips\":\"%s\"}", 
    userID, userData.getString("Username"),userData.optString("FollowedTrips", "") + 
    (userData.optString("FollowedTrips", "").isEmpty() ? "" : ",") + tripID);


    URL updateURL = new URL("https://tripfinder-2108.restdb.io/rest/tripfinderusers/"+userData.getString("_id"));
    HttpURLConnection conUpdate = (HttpURLConnection) updateURL.openConnection();
    conUpdate.setDoOutput(true);
    conUpdate.setRequestMethod("PUT");
    conUpdate.setRequestProperty("Accept", "application/json");
    conUpdate.setRequestProperty("Content-Type", "application/json");
    conUpdate.setRequestProperty("x-apikey", "62c3bc94436c0a56b1f58f6ffd0a08550d95e");
    conUpdate.setRequestProperty("cache-control", "no-cache");

    OutputStream os = conUpdate.getOutputStream();
    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
    osw.write(request);
    osw.flush();
    osw.close();
    os.close();
    conUpdate.connect();

    BufferedReader response = new BufferedReader(new InputStreamReader(conUpdate.getInputStream()));

    String lineString = response.readLine();

    return lineString;

    
}

    /**
     * PUT method for updating or creating an instance of CreateTrip
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
