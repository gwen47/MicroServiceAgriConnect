package com.actuator.actuator.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.actuator.actuator.entities.IrrigationLog;

public interface IrrigationLogRepository extends MongoRepository<IrrigationLog, String> {
  List<IrrigationLog> findByActuatorId(String actuatorId);
}