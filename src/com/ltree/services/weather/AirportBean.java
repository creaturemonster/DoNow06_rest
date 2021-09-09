package com.ltree.services.weather;

import java.math.BigDecimal;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AirportBean {
    
    public AirportBean() {
    }
    
    public AirportBean(String airportCode, String name, String associatedCity,
            Double elevation, BigDecimal latitude, BigDecimal longitude) {
        super();
        this.airportCode = airportCode;
        this.name = name;
        this.associatedCity = associatedCity;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @PathParam("code")
    private String airportCode;
    
    @QueryParam("name")
    private String name;
    
    @QueryParam("city")
    private String associatedCity;
    
    @QueryParam("elev")
    private Double elevation;
    
    @QueryParam("lat")
    private BigDecimal latitude; // decimal degrees
    
    @QueryParam("long")
    private BigDecimal longitude; // decimal degrees

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssociatedCity() {
        return associatedCity;
    }

    public void setAssociatedCity(String associatedCity) {
        this.associatedCity = associatedCity;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((airportCode == null) ? 0 : airportCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AirportBean other = (AirportBean) obj;
        if (airportCode == null) {
            if (other.airportCode != null)
                return false;
        } else if (!airportCode.equals(other.airportCode))
            return false;
        return true;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}

