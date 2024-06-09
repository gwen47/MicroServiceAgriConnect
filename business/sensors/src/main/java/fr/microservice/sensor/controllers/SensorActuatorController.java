package fr.microservice.sensor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fr.microservice.sensor.entities.Actuator;
import fr.microservice.sensor.entities.IrrigationLog;
import fr.microservice.sensor.entities.Sensor;
import fr.microservice.sensor.services.SensorActuatorService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class SensorActuatorController {

    @Autowired
    private SensorActuatorService sensorActuatorService;

    // List all sensors
    @GetMapping("/sensors")
    public List<Sensor> listAllSensors() {
        return sensorActuatorService.listAllSensors();
    }

    @GetMapping("/sensor/{code}")
    public Sensor getSensorByCode(@PathVariable String code) {
        return sensorActuatorService.getSensorByCode(code);
    }

    @PostMapping("/sensor")
    public Sensor createSensor(@RequestBody Sensor sensor) {
        return sensorActuatorService.saveSensor(sensor);
    }

    @DeleteMapping("/sensor/{code}")
    public void deleteSensorByCode(@PathVariable String code) {
        sensorActuatorService.deleteSensorByCode(code);
    }

    @GetMapping("/actuator/{code}")
    public Actuator getActuatorByCode(@PathVariable String code) {
        return sensorActuatorService.getActuatorByCode(code);
    }

    @PostMapping("/actuator")
    public Actuator createActuator(@RequestBody Actuator actuator) {
        return sensorActuatorService.saveActuator(actuator);
    }

    @DeleteMapping("/actuator/{code}")
    public void deleteActuatorByCode(@PathVariable String code) {
        sensorActuatorService.deleteActuatorByCode(code);
    }

    @PostMapping("/actuator/start-irrigation/{code}")
    public void startIrrigation(@PathVariable String code, @RequestParam long duration) {
        sensorActuatorService.startIrrigation(code, duration);
    }

    @PostMapping("/actuator/stop-irrigation/{code}")
    public void stopIrrigation(@PathVariable String code) {
        sensorActuatorService.stopIrrigation(code);
    }

    @GetMapping("/irrigation-logs")
    public List<IrrigationLog> getIrrigationLogs() {
        return sensorActuatorService.getIrrigationLogs();
    }

    @PostMapping("/sensor/update-interval/{code}")
    public void updateMeasurementInterval(@PathVariable String code, @RequestParam int interval) {
        sensorActuatorService.updateMeasurementInterval(code, interval);
    }

    @PostMapping("/sensors/update-interval")
    public void updateMeasurementIntervalForAll(@RequestParam int interval) {
        sensorActuatorService.updateMeasurementIntervalForAll(interval);
    }
}