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

    @DeleteMapping("/sensor/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorActuatorService.deleteSensor(id);
    }

    @GetMapping("/actuator/{id}")
    public Actuator getActuator(@PathVariable Long id) {
        return sensorActuatorService.getActuator(id);
    }

    @PostMapping("/actuator")
    public Actuator createActuator(@RequestBody Actuator actuator) {
        return sensorActuatorService.saveActuator(actuator);
    }

    @DeleteMapping("/actuator/{id}")
    public void deleteActuator(@PathVariable Long id) {
        sensorActuatorService.deleteActuator(id);
    }

    @PostMapping("/actuator/start-irrigation/{id}")
    public void startIrrigation(@PathVariable Long id, @RequestParam long duration) {
        sensorActuatorService.startIrrigation(id, duration);
    }

    @PostMapping("/actuator/stop-irrigation/{id}")
    public void stopIrrigation(@PathVariable Long id) {
        sensorActuatorService.stopIrrigation(id);
    }

    @GetMapping("/irrigation-logs")
    public List<IrrigationLog> getIrrigationLogs() {
        return sensorActuatorService.getIrrigationLogs();
    }

    @PostMapping("/sensor/update-interval/{id}")
    public void updateMeasurementInterval(@PathVariable Long id, @RequestParam int interval) {
        sensorActuatorService.updateMeasurementInterval(id, interval);
    }

    @PostMapping("/sensors/update-interval")
    public void updateMeasurementIntervalForAll(@RequestParam int interval) {
        sensorActuatorService.updateMeasurementIntervalForAll(interval);
    }
}
