package fr.microservice.sensor.exceptions;

public class DuplicateCodeException extends RuntimeException {
  public DuplicateCodeException(String message) {
      super(message);
  }
}
