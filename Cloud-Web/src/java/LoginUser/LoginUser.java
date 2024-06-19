/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package LoginUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import CreateTrip.CreateTrip;
import javax.ws.rs.core.Cookie;
import org.apache.commons.io.IOUtils;

/**
 * REST Web Service
 *
 * @author Alishah
 */
@Path("LoginUser")
public class LoginUser {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of loginUser
     */
    public LoginUser() {
    }

    /**
     * Retrieves representation of an instance of LoginUser.LoginUser
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam ("username") String username){
    
        try{
           
        CreateTrip createTrip = new CreateTrip();
        var userData = createTrip.findUserByName(username.toLowerCase());
        
       
        return userData.toString();
        
        }
        catch(Exception e){
            return "Username does not exist";
        }
    }

    /**
     * PUT method for updating or creating an instance of LoginUser
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
