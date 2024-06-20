package com.sensor.sensor.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sensor.sensor.entities.Sensor;

public interface SensorRepo extends MongoRepository<Sensor, String> {
  Optional<Sensor> findByCode(String code);
  boolean existsByCode(String code);
}
