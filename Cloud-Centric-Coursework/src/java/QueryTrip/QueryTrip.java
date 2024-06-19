/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package QueryTrip;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import CreateTrip.CreateTrip;
import java.io.IOException;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Alishah
 */
@Path("QueryTrip")
public class QueryTrip {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QueryTrip
     */
    public QueryTrip() {
    }

    /**
     * Retrieves representation of an instance of QueryTrip.QueryTrip
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam ("location") String location, @QueryParam ("date") String date) throws IOException {
        CreateTrip createTrip = new CreateTrip();
        var locationData = createTrip.getLocationData(location,date);
        return locationData.toString();
    }

    /**
     * PUT method for updating or creating an instance of QueryTrip
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
