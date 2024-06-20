package fr.microservice.sensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.microservice.sensor.entities.IrrigationLog;

public interface IrrigationLogRepository extends JpaRepository<IrrigationLog, Long> {
}
