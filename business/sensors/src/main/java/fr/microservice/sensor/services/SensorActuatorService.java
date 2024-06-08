package fr.microservice.sensor.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.microservice.sensor.entities.Actuator;
import fr.microservice.sensor.entities.Sensor;
import fr.microservice.sensor.repositories.ActuatorRepository;
import fr.microservice.sensor.repositories.SensorRepository;

@Service
public class SensorActuatorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ActuatorRepository actuatorRepository;

    public List<Sensor> listAllSensors() {
        return sensorRepository.findAll();
    }

    public Sensor getSensor(Long id) {
        return sensorRepository.findById(id).orElse(null);
    }

    public Actuator getActuator(Long id) {
        return actuatorRepository.findById(id).orElse(null);
    }

    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Actuator saveActuator(Actuator actuator) {
        return actuatorRepository.save(actuator);
    }
}

