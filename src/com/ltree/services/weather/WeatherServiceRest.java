package com.ltree.services.weather;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.ltree.weather.AirportInfo;
import com.ltree.weather.AirportWeather;
import com.ltree.weather.SkyConditions;
import com.ltree.weather.WeatherConditions;
import com.ltree.weather.WeatherException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTful service implemented with JAX-RS.
 * 
 * @author Mike Woinoski
 */
@Path("/weather")
public class WeatherServiceRest {
    private static Logger logger = 
            LoggerFactory.getLogger(WeatherServiceRest.class.getName());
    private static String serviceVersion = "1.0";
    
    @Inject
    private AirportWeather weatherService; // AirportWeather instance created and injected by CDI

    /**
     * Handles HTTP GET for weather service version.
     * GET http://host:8080/donow06/rest/weather/version
     */
    @GET
    @Path("/version")
    @Produces(MediaType.TEXT_PLAIN)
    public String getServiceVersion() {
        logger.debug("request for service version " + serviceVersion);
        return serviceVersion;
    }

    /**
     * Handles HTTP GET for current weather conditions. Request will be
     * GET http://host:8080/donow06/rest/weather/<airport-id>
     */
    @GET
    @Path("/airportCode")
    @Produces(MediaType.APPLICATION_XML)
    public WeatherConditions getCurrentWeather(@PathParam("airportCode") String airportCode) 
                    throws WebApplicationException {

        logger.debug("getting weather for " + airportCode);
        try {
            WeatherConditions weather = weatherService.getConditions(airportCode);
            return weather;
        } 
        catch (WeatherException e) {
            logger.error("Problem getting weather for " + airportCode, e);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Handles HTTP POST to add a new airport to the airport database. Request will be
     * POST http://host:8080/donow06/rest/weather
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addAirport(AirportInfo airportInfo, @Context UriInfo uriInfo) 
            throws WeatherException {
        logger.debug("Adding new airport: " + airportInfo);
        String serviceResult = weatherService.addAirport(airportInfo);
        
        // build the URL that a client can use to access the airport resource
        URI airportUrl = UriBuilder.fromUri(uriInfo.getRequestUri())
                                   .path("/{code}")
                                   .build(airportInfo.getAirportCode());
        
        Response response = Response.created(airportUrl)  // status 201
                                    .entity(serviceResult)
                                    .build();
        return response;
    }

    /**
     * Handles HTTP PUT to update an airport's information in the airport database. 
     * Request will be
     * PUT http://host:8080/donow06/rest/weather/<airport-code>
     */
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAirport(@PathParam("code") String airportCode, 
                                  AirportInfo airportInfo) throws WeatherException {
        logger.debug("Updating airport: " + airportInfo);
        String serviceResult = weatherService.updateAirport(airportInfo);
        
        Response response = Response.accepted()   // status 202
                                    .entity(serviceResult)
                                    .build();
        return response;
    }

    /**
     * Handles HTTP DELETE to update an airport's information in the airport database. 
     * Request URL will be
     * DELETE http://host:8080/donow06/rest/weather/<airport-code>
     */
    @DELETE
    @Path("/{code}")
    public void deleteAirport(@PathParam("code") String airportCode) 
                    throws WeatherException {
        logger.debug("Deleting airport: " + airportCode);
        weatherService.deleteAirport(airportCode);
    }

    /**
     * Handles HTTP GET for current temperature. Request will be
     * GET http://host:8080/donow06/rest/weather/<airport-id>/temp
     */
    @GET
    @Path("/{code}/temp")
    @Produces(MediaType.TEXT_PLAIN)
    public Double getCurrentTemperature(@PathParam("code") String airportCode) 
                    throws WebApplicationException {

        logger.debug("getting temperature for " + airportCode);
        try {
            Double temperature = weatherService.getTemp(airportCode);
            return temperature;
        } 
        catch (WeatherException e) {
            logger.error("Problem getting temperature for " + airportCode, e);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Handles HTTP GET for sky conditions. Request will be
     * GET http://host:8080/donow06/rest/weather/<airport-id>/sky
     */
    @GET
    @Path("/{code}/sky")
    @Produces(MediaType.APPLICATION_XML)
    public List<SkyConditions> getSkyConditions(@PathParam("code") String airportCode) 
                    throws WebApplicationException {

        logger.debug("getting sky conditions for " + airportCode);
        try {
            List<SkyConditions> skyConditions = weatherService.getSky(airportCode);
            return skyConditions;
        } 
        catch (WeatherException e) {
            logger.error("Problem getting sky conditions for " + airportCode, e);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Handles HTTP GET to add airport. Request will be
     * GET http://host:8080/donow06/rest/weather/add/<airport-id>?name=<name>&city=<associated-city>&elev=<elevation>&lat=<latitude>&long=<longitude>
     * GET http://localhost:8080/donow06/rest/weather/add/KLXV?name=Lake%20County%20Airport&city=Leadville%20CO%20US&elev=9927&lat=39.220278&long=-106.316667
     */
    @GET
    @Path("/add/{code}")
    @Produces(MediaType.APPLICATION_XML)
    public AirportInfo addAirport(@PathParam("code") String airportCode, 
                                  @QueryParam("name") String name,
                                  @QueryParam("city") String associatedCity,
                                  @QueryParam("elev") Double elevation,
                                  @QueryParam("lat") BigDecimal latitude,
                                  @QueryParam("long") BigDecimal longitude) 
                    throws WebApplicationException {

        logger.debug("adding " + airportCode + " with values from the parameter list: " + 
                airportCode + ", " + name + ", " + associatedCity + ", " + elevation + ", " +
                latitude + ", " + longitude);
        try {
            AirportInfo airportInfo = createAirportInfo(airportCode, name, associatedCity, 
                                                        elevation, latitude, longitude);
            weatherService.addAirport(airportInfo);
            return airportInfo;
        } 
        catch (WeatherException e) {
            logger.error("Problem add values for " + airportCode, e);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Handles HTTP GET to add airport. Request will be
     * GET http://host:8080/donow06/rest/weather/addbean/<airport-id>?name=<name>&city=<associated-city>&elev=<elevation>&lat=<latitude>&long=<longitude>
     * GET http://localhost:8080/donow06/rest/weather/addbean/KLXV?name=Lake%20County%20Airport&city=Leadville%20CO%20US&elev=9927&lat=39.220278&long=-106.316667
     */
    @GET
    @Path("/addbean/{code}")
    @Produces(MediaType.APPLICATION_XML)
    public AirportInfo addAirport(@BeanParam AirportBean airportBean) 
                    throws WebApplicationException {

        logger.debug("adding " + airportBean);
        try {
            AirportInfo airportInfo = createAirportInfo(airportBean);
            weatherService.addAirport(airportInfo);
            return airportInfo;
        } 
        catch (WeatherException e) {
            logger.error("Problem adding " + airportBean, e);
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Method for testing web service deployment. 
     * Echoes its argument. Request will be
     * GET http://host:8080/donow06/rest/weather/echo/<arg>
     */
    @GET
    @Path("/echo/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(@PathParam("message") String msg) {
        logger.debug("received GET request to echo \"" + msg + "\"");
        return msg;
    }

    private AirportInfo createAirportInfo(String airportCode, String name, 
            String associatedCity, Double elevation, BigDecimal latitude,
            BigDecimal longitude) {
        AirportInfo airportInfo = new AirportInfo();
        airportInfo.setAirportCode(airportCode);
        airportInfo.setName(name);
        airportInfo.setAssociatedCity(associatedCity);
        airportInfo.setElevation(elevation);
        airportInfo.setLatitude(latitude);
        airportInfo.setLongitude(longitude);
        return airportInfo;
    }

    private AirportInfo createAirportInfo(AirportBean airportBean) {
        return createAirportInfo(airportBean.getAirportCode(), airportBean.getName(), 
                airportBean.getAssociatedCity(), airportBean.getElevation(), 
                airportBean.getLatitude(), airportBean.getLongitude());
    }
}
