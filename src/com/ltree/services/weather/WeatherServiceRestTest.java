package com.ltree.services.weather;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

import static org.easymock.EasyMock.*;

import com.ltree.weather.AirportInfo;
import com.ltree.weather.AirportWeatherImpl;
import com.ltree.weather.SkyConditions;
import com.ltree.weather.WeatherConditions;
import com.ltree.weather.WeatherException;

/**
 * JUnit tests for JAX-RS resource class WeatherServiceRest.
 * To run these tests with the Eclipse JUnit test runner, you must add the unit test
 * libraries and Jersey server libraries to the run configuration classpath: 
 *  Run > Run Configurations > JUnit > WeatherServiceRestTest > Classpath
 *  Select User Entries > Advanced > Add Classpath Variables > OK > CRS577_LIB > Extend
 *      Select all jars under unitTest, jersey/api, jersey/ext, jersey/lib
 *  
 * @author Mike Woinoski mike@woinoski.com
 *
 */
public class WeatherServiceRestTest {
    private WeatherServiceRest weatherServiceRest;
    private AirportWeatherImpl airportWeatherMock;

    @Before
    public void setUp() throws Exception {
        weatherServiceRest = new WeatherServiceRest();

        airportWeatherMock = createMock(AirportWeatherImpl.class);
        Whitebox.setInternalState(weatherServiceRest, airportWeatherMock);
        
        Logger loggerMock = createNiceMock(Logger.class);
        Whitebox.setInternalState(WeatherServiceRest.class, loggerMock); // disable logging
    }

    @Test
    public void testAddAirport() throws Exception {
        AirportInfo airportInfo = createAirportInfo("ATL",
                "Hartsfield - Jackson Atlanta Intl", 1026.0, "Atlanta GA US",
                "33.6366995", "84.4278639");
        String uriString = "http://myhost/myapp/weather";
        URI uri = new URI(uriString);
        String responseEntity = "success";

        UriInfo uriInfoMock = createMock(UriInfo.class);
        expect(uriInfoMock.getRequestUri()).andReturn(uri);
        replay(uriInfoMock);
        expect(airportWeatherMock.addAirport(airportInfo))
            .andReturn(responseEntity);
        replay(airportWeatherMock);

        Response response = weatherServiceRest.addAirport(airportInfo,
                uriInfoMock);

        verify(airportWeatherMock);
        assertEquals(201, response.getStatus());
        assertEquals(uriString + "/ATL", response.getHeaderString("Location"));
        assertEquals(responseEntity, response.getEntity());
    }

    @Test
    public void testUpdateAirport() throws Exception {
        String airportCode = "ATL";
        AirportInfo airportInfo = createAirportInfo(airportCode,
                "Hartsfield - Jackson Atlanta Intl", 1026.0, "Atlanta GA US",
                "33.6366995", "84.4278639");
        String responseEntity = "success";

        expect(airportWeatherMock.updateAirport(airportInfo))
            .andReturn(responseEntity);
        replay(airportWeatherMock);

        Response response = weatherServiceRest.updateAirport(airportCode, airportInfo);

        verify(airportWeatherMock);
        assertEquals(202, response.getStatus());
        assertEquals(responseEntity, response.getEntity());
    }

    @Test
    public void testDeleteAirport() throws Exception {
        String airportCode = "ATL";

        airportWeatherMock.deleteAirport(airportCode);
        replay(airportWeatherMock);

        weatherServiceRest.deleteAirport(airportCode);

        verify(airportWeatherMock);
    }

    @Test
    public void testGetCurrentWeatherOk() throws Exception {
        String airportCode = "ATL";

        WeatherConditions expected = new WeatherConditions();
        
        expect(airportWeatherMock.getConditions(airportCode))
            .andReturn(expected);
        replay(airportWeatherMock);

        WeatherConditions actual = weatherServiceRest.getCurrentWeather(airportCode);

        verify(airportWeatherMock);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrentWeatherException() throws Exception {
        String airportCode = "ATL";

        expect(airportWeatherMock.getConditions(airportCode))
            .andThrow(new WeatherException());
        replay(airportWeatherMock);

        try {
            weatherServiceRest.getCurrentWeather(airportCode);
            fail("should have thrown WebApplicationException");
        } catch (WebApplicationException e) {
            assertEquals(500, e.getResponse().getStatus());
        }
    }

    @Test
    public void testGetCurrentTemperature() throws Exception {
        String airportCode = "ATL";
        Double expected = new Double(72.0);
        expect(airportWeatherMock.getTemp(airportCode))
            .andReturn(expected);
        replay(airportWeatherMock);

        Double actual = weatherServiceRest.getCurrentTemperature(airportCode);

        verify(airportWeatherMock);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSkyConditions() throws Exception {
        String airportCode = "ATL";

        List<SkyConditions> expected = new ArrayList<>();
        
        expect(airportWeatherMock.getSky(airportCode))
            .andReturn(expected);
        replay(airportWeatherMock);

        List<SkyConditions> actual = weatherServiceRest.getSkyConditions(airportCode);

        verify(airportWeatherMock);
        assertEquals(expected, actual);
    }

    private AirportInfo createAirportInfo(String code, String name,
            Double elev, String city, String lat, String lng) {
        AirportInfo airportInfo = new AirportInfo();
        airportInfo.setAirportCode(code);
        airportInfo.setName(name);
        airportInfo.setElevation(elev);
        airportInfo.setAssociatedCity(city);
        airportInfo.setLatitude(new BigDecimal(lat));
        airportInfo.setLongitude(new BigDecimal(lng));
        return airportInfo;
    }

}
