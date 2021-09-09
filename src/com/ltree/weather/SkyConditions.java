/* Chapter 7 Sample */

package com.ltree.weather;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class SkyConditions implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum CloudLayerDescription { scattered, broken, overcast };

    private int altitude;  // feet
    private CloudLayerDescription cloudLayer; 

    public SkyConditions() {
    }
    
    public SkyConditions(int altitude, CloudLayerDescription cloudLayer) {
        this.altitude = altitude;
        this.cloudLayer = cloudLayer;
    }

    public int getAltitude() {
        return altitude;
    }
    
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public CloudLayerDescription getCloudLayer() {
        return cloudLayer;
    }
    
    public void setCloudLayer(CloudLayerDescription cloudLayer) {
        this.cloudLayer = cloudLayer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
