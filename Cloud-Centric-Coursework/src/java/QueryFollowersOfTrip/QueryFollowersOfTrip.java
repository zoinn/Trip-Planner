/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package QueryFollowersOfTrip;

import CreateTrip.CreateTrip;
import FollowTrip.FollowTrip;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Alishah
 */
@Path("QueryFollowersOfTrip")
public class QueryFollowersOfTrip {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QueryFollowersOfTrip
     */
    public QueryFollowersOfTrip() {
    }

    /**
     * Retrieves representation of an instance of QueryFollowersOfTrip.QueryFollowersOfTrip
     *
     * @param location the location to query followers for
     * @return a JSON array of usernames
     * @throws IOException if an error occurs during processing
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("location") String location) throws IOException {

        CreateTrip createTrip = new CreateTrip(); 
        FollowTrip followTrip = new FollowTrip();
        String tripDataString = followTrip.getTripJSON(location);
        System.out.println("THIS IS THE TRIP DATA!!! ::: "+tripDataString);
        if (!"Location Not Found".equals(tripDataString)){

            JSONObject locationData = new JSONObject(tripDataString);
            String usersFollowing = locationData.optString("UsersFollowing", "");
            JSONObject usersJson = new JSONObject();
            JSONArray usersArray = new JSONArray();               
            String[] followers = usersFollowing.split("\\s*,\\s*");

            for (String follower : followers) {
                follower = follower.trim();
                var userData = createTrip.findUserByID(follower);
                usersArray.put(userData.getString("Username"));
                }
            usersJson.put("Users", usersArray);           
            return usersJson.toString();

            }
           return "Location Not Found";
        }

                
                
    


    /**
     * PUT method for updating or creating an instance of QueryFollowersOfTrip
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
        // Implement this method if needed
    }
}
