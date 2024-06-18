package com.sensor.sensor.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sensorLogs")
public class SensorLog {

    @Id
    private String id;

    private String sensorCode; // Code unique du capteur
    private double temperature; // Température enregistrée
    private double humidity; // Humidité enregistrée
    private LocalDateTime timestamp; // Moment de l'enregistrement

    // Constructeurs, Getters et Setters
    public SensorLog() {
    }

    public SensorLog(String sensorCode, double temperature, double humidity, LocalDateTime timestamp) {
        this.sensorCode = sensorCode;
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorCode() {
        return sensorCode;
    }

    public void setSensorCode(String sensorCode) {
        this.sensorCode = sensorCode;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}