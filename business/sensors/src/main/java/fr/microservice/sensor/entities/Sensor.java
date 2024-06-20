package fr.microservice.sensor.entities;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Sensor extends SensorActuator {

    private double temperature;
    private double humidity;
    private LocalDateTime lastUpdated;
    private int measurementInterval; // in seconds

    // Getters and Setters
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getMeasurementInterval() {
        return measurementInterval;
    }

    public void setMeasurementInterval(int measurementInterval) {
        this.measurementInterval = measurementInterval;
    }
}
