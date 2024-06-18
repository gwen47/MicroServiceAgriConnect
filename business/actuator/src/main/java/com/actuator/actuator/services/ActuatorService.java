package com.actuator.actuator.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actuator.actuator.entities.Actuator;
import com.actuator.actuator.repositories.ActuatorRepository;

@Service
public class ActuatorService {

    @Autowired
    private ActuatorRepository actuatorRepository;

    public Actuator saveActuator(Actuator actuator) {
        return actuatorRepository.save(actuator);
    }

    public List<Actuator> getAllActuators() {
        return actuatorRepository.findAll();
    }

    public Optional<Actuator> getActuatorByCode(String code) {
        return actuatorRepository.findByCode(code);
    }

    public void deleteActuatorByCode(String code) {
        actuatorRepository.findByCode(code).ifPresent(actuatorRepository::delete);
    }
}