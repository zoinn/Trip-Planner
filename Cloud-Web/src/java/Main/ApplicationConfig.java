package Main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Alishah
 */
@javax.ws.rs.ApplicationPath("Tripfinder")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(CreateTrip.CreateTrip.class);
        resources.add(FollowTrip.FollowTrip.class);
        resources.add(GenerateID.GenerateID.class);
        resources.add(LoginUser.LoginUser.class);
        resources.add(QueryFollowersOfTrip.QueryFollowersOfTrip.class);
        resources.add(QueryTrip.QueryTrip.class);
        resources.add(RegisterUser.RegisterUser.class);
    }
    
}
