package com.actuator.actuator.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.actuator.actuator.entities.Actuator;

public interface ActuatorRepository extends MongoRepository<Actuator, String> {
  Optional<Actuator> findByCode(String code);
}
