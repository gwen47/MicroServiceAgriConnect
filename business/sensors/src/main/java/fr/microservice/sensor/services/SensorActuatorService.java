package fr.microservice.sensor.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.microservice.sensor.entities.Actuator;
import fr.microservice.sensor.entities.IrrigationLog;
import fr.microservice.sensor.entities.Sensor;
import fr.microservice.sensor.exceptions.DuplicateCodeException;
import fr.microservice.sensor.exceptions.ResourceNotFoundException;
import fr.microservice.sensor.repositories.ActuatorRepository;
import fr.microservice.sensor.repositories.IrrigationLogRepository;
import fr.microservice.sensor.repositories.SensorRepository;

@Service
public class SensorActuatorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ActuatorRepository actuatorRepository;

    @Autowired
    private IrrigationLogRepository irrigationLogRepository;

    // List all sensors 
    public List<Sensor> listAllSensors() {
        return sensorRepository.findAll();
    }

    public Sensor getSensorByCode(String code) {
        return sensorRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
    }

    public Actuator getActuatorByCode(String code) {
        return actuatorRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Actuator not found with code: " + code));
    }

    public Sensor getSensorByCoordinates(double latitude, double longitude) {
        return sensorRepository.findByLatitudeAndLongitude(latitude, longitude)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with coordinates: " + latitude + ", " + longitude));
    }

    public Actuator getActuatorByCoordinates(double latitude, double longitude) {
        return actuatorRepository.findByLatitudeAndLongitude(latitude, longitude)
                .orElseThrow(() -> new ResourceNotFoundException("Actuator not found with coordinates: " + latitude + ", " + longitude));
    }
    
    public Sensor saveSensor(Sensor sensor) {
        if (sensorRepository.existsByCode(sensor.getCode())) {
            throw new DuplicateCodeException("Sensor with code " + sensor.getCode() + " already exists.");
        }
        return sensorRepository.save(sensor);
    }

    public Actuator saveActuator(Actuator actuator) {
        if (actuatorRepository.existsByCode(actuator.getCode())) {
            throw new DuplicateCodeException("Actuator with code " + actuator.getCode() + " already exists.");
        }
        return actuatorRepository.save(actuator);
    }

    public void deleteSensorByCode(String code) {
        Sensor sensor = sensorRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + code));
        sensorRepository.delete(sensor);
    }

    public void deleteActuatorByCode(String code) {
        Actuator actuator = actuatorRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Actuator not found with code: " + code));
        actuatorRepository.delete(actuator);
    }

    public void startIrrigation(String actuatorCode, long duration) {
        Actuator actuator = actuatorRepository.findByCode(actuatorCode)
                .orElseThrow(() -> new ResourceNotFoundException("Actuator not found with code: " + actuatorCode));
        actuator.setIrrigating(true);
        actuator.setIrrigationDuration(duration);
        actuatorRepository.save(actuator);

        IrrigationLog log = new IrrigationLog();
        log.setActuator(actuator);
        log.setStartTime(LocalDateTime.now());
        log.setStatus("started");
        irrigationLogRepository.save(log);
    }

    public void stopIrrigation(String actuatorCode) {
        Actuator actuator = actuatorRepository.findByCode(actuatorCode)
                .orElseThrow(() -> new ResourceNotFoundException("Actuator not found with code: " + actuatorCode));
        actuator.setIrrigating(false);
        actuator.setIrrigationDuration(0);
        actuatorRepository.save(actuator);

        IrrigationLog log = new IrrigationLog();
        log.setActuator(actuator);
        log.setEndTime(LocalDateTime.now());
        log.setStatus("ended");
        irrigationLogRepository.save(log);
    }

    public List<IrrigationLog> getIrrigationLogs() {
        return irrigationLogRepository.findAll();
    }

    public void updateMeasurementInterval(String sensorCode, int interval) {
        Sensor sensor = sensorRepository.findByCode(sensorCode)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found with code: " + sensorCode));
        sensor.setMeasurementInterval(interval);
        sensorRepository.save(sensor);
    }

    public void updateMeasurementIntervalForAll(int interval) {
        List<Sensor> sensors = sensorRepository.findAll();
        for (Sensor sensor : sensors) {
            sensor.setMeasurementInterval(interval);
        }
        sensorRepository.saveAll(sensors);
    }
}