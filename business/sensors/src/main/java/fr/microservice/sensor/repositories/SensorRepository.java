package fr.microservice.sensor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.microservice.sensor.entities.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
  Optional<Sensor> findByCode(String code);
  Optional<Sensor> findByLatitudeAndLongitude(double latitude, double longitude);
  boolean existsByCode(String code);

}
