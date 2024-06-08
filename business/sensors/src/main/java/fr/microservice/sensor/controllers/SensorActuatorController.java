package fr.microservice.sensor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.microservice.sensor.entities.Actuator;
import fr.microservice.sensor.entities.Sensor;
import fr.microservice.sensor.services.SensorActuatorService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SensorActuatorController {

    @Autowired
    private SensorActuatorService sensorActuatorService;

    @GetMapping("/sensors")
    public List<Sensor> listAllSensors() {
        return sensorActuatorService.listAllSensors();
    }

    @GetMapping("/sensor/{id}")
    public Sensor getSensor(@PathVariable Long id) {
        return sensorActuatorService.getSensor(id);
    }

    @PostMapping("/sensor")
    public Sensor createSensor(@RequestBody Sensor sensor) {
        return sensorActuatorService.saveSensor(sensor);
    }

    @GetMapping("/actuator/{id}")
    public Actuator getActuator(@PathVariable Long id) {
        return sensorActuatorService.getActuator(id);
    }

    @PostMapping("/actuator")
    public Actuator createActuator(@RequestBody Actuator actuator) {
        return sensorActuatorService.saveActuator(actuator);
    }
}
