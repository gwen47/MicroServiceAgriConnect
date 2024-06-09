package fr.microservice.sensor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.microservice.sensor.entities.Actuator;

public interface ActuatorRepository extends JpaRepository<Actuator, Long> {
  Optional<Actuator> findByCode(String code);
  Optional<Actuator> findByLatitudeAndLongitude(double latitude, double longitude);
  boolean existsByCode(String code);
}
