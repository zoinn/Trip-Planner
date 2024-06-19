package FollowTrip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import CreateTrip.CreateTrip;

/**
 * REST Web Service
 *
 * @author Alishah
 */
@Path("FollowTrip")
public class FollowTrip {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FollowTripResource
     */
    public FollowTrip() {
    }

    /**
     * Retrieves representation of an instance of FollowTrip.FollowTrip
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("location") String location, @QueryParam("date") String date,
            @QueryParam("userID") String userID) {
        try {
            CreateTrip createTrip = new CreateTrip();
            JSONObject tripJSON = updateTripFollowers(userID, location);
            createTrip.updateUserFollowing(userID, tripJSON.getString("TripID"));
            // Returning null might be confusing, consider returning a success message or
            // relevant JSON response.
            return "Successfully followed the trip!";
        } catch (Exception e) {
            // Returning a generic error message, you might want to log the exception for
            // debugging purposes.
            return "Location Not Found";
        }
    }

    public JSONObject updateTripFollowers(String userID, String location) throws IOException {
        
        try{
        
            JSONObject tripJSON = new JSONObject(getTripJSON(location));

            String request = String.format("{\"TripID\":\"%s\",\"LocationData\":%s,\"UsersFollowing\":\"%s\"}",
                    tripJSON.getString("TripID"), tripJSON.getJSONObject("LocationData").toString(),
                    tripJSON.optString("UsersFollowing", "") + (tripJSON.optString("UsersFollowing", "").isEmpty() ? ""
                            : ",") + userID);

            URL updateURL = new URL(
                    "https://tripfinder-2108.restdb.io/rest/tripfindertrips/" + tripJSON.getString("_id"));
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
          
            BufferedReader response = new BufferedReader(new InputStreamReader(conUpdate.getInputStream()));
            StringBuilder responseStringBuilder = new StringBuilder();
            String lineString;
            while ((lineString = response.readLine()) != null) {
                responseStringBuilder.append(lineString);
            }

            return new JSONObject(responseStringBuilder.toString());
            }
            catch(Exception e){
                return new JSONObject("Location Not Found");
            }
    }
        
    

    public String getTripJSON(String location) throws MalformedURLException, IOException {
        URL getURL = new URL("https://tripfinder-2108.restdb.io/rest/tripfindertrips");
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

            JSONArray jsonArray = new JSONArray(jsonResponse.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tripObject = jsonArray.getJSONObject(i);
                JSONArray requestArray = tripObject.getJSONObject("LocationData").getJSONObject("data")
                        .getJSONArray("request");
                JSONObject requestObject = requestArray.getJSONObject(0);
                String query = requestObject.getString("query");
                if (query.equalsIgnoreCase(location)) {
                    return tripObject.toString();
                }
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return "Location Not Found INSIDE";
        }

        return "Location Not Found OUTSIDE";
    }

    /**
     * PUT method for updating or creating an instance of FollowTrip
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
        // Implement if needed
    }
}
