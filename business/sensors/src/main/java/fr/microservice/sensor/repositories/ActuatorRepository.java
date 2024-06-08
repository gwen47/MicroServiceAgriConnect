package fr.microservice.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.microservice.sensor.entities.Actuator;

public interface ActuatorRepository extends JpaRepository<Actuator, Long> {
}
