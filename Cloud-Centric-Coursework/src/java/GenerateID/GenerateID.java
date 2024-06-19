/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package GenerateID;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
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
import org.apache.tomcat.util.json.JSONParser;

/**
 * REST Web Service
 *
 * @author Alishah
 */
@Path("GenerateID")
public class GenerateID {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenerateID
     */
    public GenerateID() {
    }

    /**
     * Retrieves representation of an instance of GenerateID.GenerateID
     * @param x integer
     * @param y integer
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws MalformedURLException, IOException{
        
        String request = "{\n" +
"    \"jsonrpc\": \"2.0\",\n" +
"    \"method\": \"generateUUIDs\",\n" +
"    \"params\": {\n" +
"        \"apiKey\": \"1c33b4ca-ad0e-49c4-b954-cd210fbe4e27\",\n" +
"        \"n\": 1 \n" +
"    },\n" +
"    \"id\": 15998\n" +
"}";
        
        URL url = new URL("https://api.random.org/json-rpc/4/invoke");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
        osw.write(request);
        osw.flush();
        osw.close();
        os.close();  //don't forget to close the OutputStream
        con.connect();
        
        BufferedReader response = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String lineString = response.readLine();
        
        return lineString;
        
    }
    
    /**
     * PUT method for updating or creating an instance of GenerateID
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
