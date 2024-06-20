package fr.microservice.sensor.entities;

import jakarta.persistence.Entity;

@Entity
public class Actuator extends SensorActuator {

  private boolean isIrrigating;
  private long irrigationDuration; // in seconds

  // Getters and Setters
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

