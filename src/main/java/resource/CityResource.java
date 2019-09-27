package resource;

import entity.City;
import service.CityService;
import service.impl.CityServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public class CityResource {

    //create a city
    @POST
    @Path("/cities")
    @Produces("application/json")
    public Response createCity(@FormParam("name") String name){
        CityServiceImpl cityService = new CityServiceImpl();
        return Response.status(200)
                .entity(cityService.createCity(name))
                .build();
    }

    //get city by id
    @GET
    @Path("cities/{id}")
    @Produces("application/json")
    public Response getCity(@PathParam("id") Long id) throws IOException {
        CityServiceImpl cityService = new CityServiceImpl();
        return Response.status(200)
                .entity(cityService.getCityById(id))
                .build();
    }

    //get all cities
    @GET
    @Path("/cities")
    @Produces("application/json")
    public Response getCities(){
        CityServiceImpl cityService = new CityServiceImpl();
        return Response.status(200)
                .entity(cityService.getAllCities())
                .build();
    }
}
