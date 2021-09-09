/* Chapter 2 Sample */

package com.ltree.weather;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of an airport weather service front end.
 */
@ApplicationScoped
public class AirportWeatherImpl implements AirportWeather {

    @Override
    public String addAirport(AirportInfo airportInfo) throws WeatherException {
        // Stub implementation
        return airportInfo.getName();
    }

    @Override
    public String updateAirport(AirportInfo airportInfo) throws WeatherException {
        // Stub implementation        
        return airportInfo.getName();
    }

    @Override
    public void deleteAirport(String airportCode) throws WeatherException {
        // Stub implementation
    }
        
    /** Get the temperature at the given airport. */
    @Override
    public Double getTemp(String airportCode) throws WeatherException {
        if (airportCode == null) {
            throw new WeatherException("airport code is null");
        }
        // Stub implementation        
        Double temp = 25.7;
        // get temperature from weather station
        return temp;
    }

    /** Get the relative humidity at the given airport. */
    @Override
    public Double getHumidity(String airportCode) throws WeatherException {
        if (airportCode == null) {
            throw new WeatherException("airport code is null");
        }
        // Stub implementation        
        Double humidity = 56.5;
        // get humidity from weather station
        return humidity;
    }

    /** Gets the barometric pressure (in millibars) at the given airport. */
    @Override
    public Double getBarometer(String airportCode) throws WeatherException {
        if (airportCode == null) {
            throw new WeatherException("airport code is null");
        }
        // Stub implementation        
        Double baram = 1013.2;
        // get barometric pressure from weather station
        return baram;
    }

    /** Gets the visibility at the given airport. */
    @Override
    public Integer getVisibility(String airportCode) throws WeatherException {
        if (airportCode == null) {
            throw new WeatherException("airport code is null");
        }
        // Stub implementation        
        Integer visibility = 15;
        // get temperature from weather station
        return visibility;
    }

    /**
     * Gets the wind at the given airport. Requires speed and direction
     */
    @Override
    public WindConditions getWind(String airportCode) throws WeatherException {
        if (airportCode == null) {
            throw new WeatherException("airport code is null");
        }
        // Stub implementation        
        WindConditions cond = null;
        // get wind conditions from weather station
        cond = new WindConditions(8, 270);
        return cond;
    }

    /** Gets the sky conditions at the given airport. */
    @Override
    public List<SkyConditions> getSky(String airportCode) throws WeatherException {
        if (airportCode == null) {
            throw new WeatherException("airport code is null");
        }
        // Stub implementation        
        // get sky conditions from weather station
        SkyConditions[] skyConditions = {
            new SkyConditions(5000, SkyConditions.CloudLayerDescription.scattered),
            new SkyConditions(7500, SkyConditions.CloudLayerDescription.broken),
        };
        return Arrays.asList(skyConditions);
    }

    /**
     * Coarse-grained operation to return all weather conditions in a Transfer
     * Object
     */
    @Override
    public WeatherConditions getConditions(String airportCode)
            throws WeatherException {
        if (airportCode.equalsIgnoreCase("fail")) {
            throw new WeatherException("Can't get weather conditions");
        }
        WeatherConditions conditions = new WeatherConditions();
        conditions.setAirportCode(airportCode);
        conditions.setTemp(getTemp(airportCode));
        conditions.setHumidity(getHumidity(airportCode));
        conditions.setBarometer(getBarometer(airportCode));
        conditions.setVisibility(getVisibility(airportCode));
        conditions.setWind(getWind(airportCode));
        conditions.setSky(getSky(airportCode));
        return conditions;
    }

}
