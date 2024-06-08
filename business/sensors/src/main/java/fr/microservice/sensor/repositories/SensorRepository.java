package fr.microservice.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.microservice.sensor.entities.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
