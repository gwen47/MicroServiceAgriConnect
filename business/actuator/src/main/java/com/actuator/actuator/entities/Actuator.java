package com.actuator.actuator.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actuators")
public class Actuator {

    @Id
    private String id;

    private String code;
    private double latitude;
    private double longitude;
    private boolean isIrrigating;
    private long irrigationDuration; // in seconds

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isIrrigating() {
        return isIrrigating;
    }

    public void setIrrigating(boolean isIrrigating) {
        this.isIrrigating = isIrrigating;
    }

    public long getIrrigationDuration() {
        return irrigationDuration;
    }

    public void setIrrigationDuration(long irrigationDuration) {
        this.irrigationDuration = irrigationDuration;
    }
}
