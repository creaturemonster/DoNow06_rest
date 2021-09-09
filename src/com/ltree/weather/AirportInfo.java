package com.ltree.weather;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class AirportInfo implements Serializable {
    private static final long serialVersionUID = 1;
    
    private String airportCode;
    private String name;
    private String associatedCity;
    private Double elevation;
    private BigDecimal latitude; // decimal degrees
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
        AirportInfo other = (AirportInfo) obj;
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

