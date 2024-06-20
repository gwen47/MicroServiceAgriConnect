package fr.microservice.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.microservice.sensor.entities.SensorActuator;

public interface SensorActuatorRepository extends JpaRepository<SensorActuator, Long> {
}
