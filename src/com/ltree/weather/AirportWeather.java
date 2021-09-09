package com.ltree.weather;

import java.util.List;

/**
 * Interface for the Airport Weather service
 */
public interface AirportWeather {

    /** Adds information about a new airport */
    public String addAirport(AirportInfo airportInfo) throws WeatherException;

    /** Updates information about a airport */
    public String updateAirport(AirportInfo airportInfo) throws WeatherException;

    /** Deletes information about a airport */
    public void deleteAirport(String airportCode) throws WeatherException;

    /** Get the temperature at the given airport. */
    public Double getTemp(String airportCode) throws WeatherException;

    /** Get the relative humidity at the given airport. */
    public Double getHumidity(String airportCode) throws WeatherException;

    /** Gets the barometric pressure (in mm) at the given airport. */
    public Double getBarometer(String airportCode) throws WeatherException;

    /** Gets the visibility at the given airport. */
    public Integer getVisibility(String airportCode) throws WeatherException;

    /** Gets the wind (speed and direction) at the given airport. */
    public WindConditions getWind(String airportCode) throws WeatherException;

    /** Gets the sky conditions (cloud type and elevation above MSL) at the given airport. */
    public List<SkyConditions> getSky(String airportCode) throws WeatherException;

    /**
     * Coarse-grained operation to return all weather conditions in a
     * Data Transfer Object (DTO).
     */
    public WeatherConditions getConditions(String airportCode)
            throws WeatherException;
    
}
