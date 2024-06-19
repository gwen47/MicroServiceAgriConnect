package com.sensor.sensor.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sensor.sensor.entities.SensorLog;

public interface SensorLogRepository extends MongoRepository<SensorLog, String> {
  List<SensorLog> findAllBySensorCode(String sensorCode);
}