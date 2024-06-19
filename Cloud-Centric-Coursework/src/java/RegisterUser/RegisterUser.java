package RegisterUser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Alishah
 */
@Path("RegisterUser")
public class RegisterUser {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam ("username") String username, @QueryParam ("userID") String userID) {
    
        try{
            
        
        String request = String.format("{\"UserID\":\"%s\",\"FollowedTrips\":\"%s\",\"Username\":\"%s\"}", 
        userID,"",username.toLowerCase());

        URL url = new URL("https://tripfinder-2108.restdb.io/rest/tripfinderusers");
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
        
        return lineString;
        }
        catch(Exception e){
            return "Username Taken, Choose Another";
        }
        
    }
}
