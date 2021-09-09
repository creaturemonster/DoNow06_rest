package com.ltree.weather;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/** 
 * WeatherConditions defines a Data Transfer Object (DTO) for all weather conditions. 
 */ 
@XmlRootElement
public class WeatherConditions implements Serializable {
    private String airportCode;
    private Double temp;
    private Double humidity;
    private Double barometer;
    private Integer visibility;
    private WindConditions wind;
    private List<SkyConditions> sky;

    public WeatherConditions() {}
    
    public WeatherConditions(String airportCode, Double barometer,
            Double humidity, List<SkyConditions> sky, Double temp,
            Integer visibility, WindConditions wind) {
        this.airportCode = airportCode;
        this.barometer = barometer;
        this.humidity = humidity;
        this.sky = sky;
        this.temp = temp;
        this.visibility = visibility;
        this.wind = wind;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getBarometer() {
        return barometer;
    }

    public void setBarometer(Double barometer) {
        this.barometer = barometer;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public WindConditions getWind() {
        return wind;
    }

    public void setWind(WindConditions wind) {
        this.wind = wind;
    }

    public List<SkyConditions> getSky() {
        return sky;
    }

    public void setSky(List<SkyConditions> sky) {
        this.sky = sky;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
