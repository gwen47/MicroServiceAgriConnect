package com.sensor.sensor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sensor.sensor.entities.Sensor;
import com.sensor.sensor.entities.SensorLog;
import com.sensor.sensor.exceptions.DuplicateCodeException;
import com.sensor.sensor.exceptions.ResourceNotFoundException;
import com.sensor.sensor.services.SensorLogService;
import com.sensor.sensor.services.SensorService;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorLogService sensorLogService; // Injection du service de log

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor) {
        if (sensorService.existsByCode(sensor.getCode())) {
            throw new DuplicateCodeException("Sensor with code " + sensor.getCode() + " already exists.");
        }
        Sensor newSensor = sensorService.saveSensor(sensor);
        // Log de la création du capteur
        sensorLogService.logSensorReading(newSensor.getCode(), newSensor.getTemperature(), newSensor.getHumidity());
        return ResponseEntity.ok(newSensor);
    }

    @GetMapping
    public List<Sensor> getAllSensors() {
        return sensorService.getAllSensors();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable String code) {
        Sensor sensor = sensorService.getSensorByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteSensor(@PathVariable String code) {
        Sensor sensor = sensorService.getSensorByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
        sensorService.deleteSensor(sensor.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{code}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable String code, @RequestBody Sensor sensorDetails) {
        Sensor existingSensor = sensorService.getSensorByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
        existingSensor.setCode(sensorDetails.getCode());
        existingSensor.setLatitude(sensorDetails.getLatitude());
        existingSensor.setLongitude(sensorDetails.getLongitude());
        existingSensor.setTemperature(sensorDetails.getTemperature());
        existingSensor.setHumidity(sensorDetails.getHumidity());
        existingSensor.setMeasurementInterval(sensorDetails.getMeasurementInterval());
        Sensor updatedSensor = sensorService.saveSensor(existingSensor);
        // Log de la mise à jour du capteur
        sensorLogService.logSensorReading(updatedSensor.getCode(), updatedSensor.getTemperature(), updatedSensor.getHumidity());
        return ResponseEntity.ok(updatedSensor);
    }

    // rajotue une méthode permettant de récupérer les dernières mesures d'un capteur avec les logs 
    @GetMapping("/{code}/logs")
    public ResponseEntity<List<SensorLog>> getSensorLogs(@PathVariable String code) {
        Sensor sensor = sensorService.getSensorByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
        List<SensorLog> logs = sensorLogService.getSensorLogsByCode(sensor.getCode());
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/update-interval")
    public void updateMeasurementIntervalForAll(@RequestParam int interval) {
        sensorService.updateMeasurementIntervalForAll(interval);
    }

    // rajouter une méthode permettant de mettre à jour l'intervalle de mesure pour un seul capteur
    @PostMapping("/{code}/update-interval")
    public ResponseEntity<Sensor> updateMeasurementIntervalForOne(@PathVariable String code, @RequestParam int interval) {
        Sensor sensor = sensorService.getSensorByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
        sensor.setMeasurementInterval(interval);
        Sensor updatedSensor = sensorService.saveSensor(sensor);
        return ResponseEntity.ok(updatedSensor);
    }

}