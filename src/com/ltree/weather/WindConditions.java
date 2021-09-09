/* Chapter 7 Sample */

package com.ltree.weather;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlRootElement
public class WindConditions implements Serializable {
    private int velocity;  // knots
    private int degrees; // compass point: 0 == North, 90 == East
    
    public WindConditions() {
    }
    
    public WindConditions(int speed, int direction) {
        this.velocity = speed;
        this.degrees = direction;
    }
    
    public int getSpeed() {
        return velocity;
    }
    
    public void setSpeed(int speed) {
        this.velocity = speed;
    }
    
    /**
     * Returns wind direction in compass degrees.
     * 0 == 360 == North
     * 90 == East
     * 180 == South
     * 270 == West
     */
    public int getDirection() {
        return degrees;
    }
    
    public void setDirection(int direction) {
        this.degrees = direction;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
