package fr.microservice.sensor.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.microservice.sensor.entities.Actuator;
import fr.microservice.sensor.entities.IrrigationLog;
import fr.microservice.sensor.entities.Sensor;
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

    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    public void deleteActuator(Long id) {
        actuatorRepository.deleteById(id);
    }

    public void startIrrigation(Long actuatorId, long duration) {
        Actuator actuator = actuatorRepository.findById(actuatorId).orElseThrow(() -> new RuntimeException("Actuator not found"));
        actuator.setIrrigating(true);
        actuator.setIrrigationDuration(duration);
        actuatorRepository.save(actuator);

        IrrigationLog log = new IrrigationLog();
        log.setActuator(actuator);
        log.setStartTime(LocalDateTime.now());
        log.setStatus("started");
        irrigationLogRepository.save(log);
    }

    public void stopIrrigation(Long actuatorId) {
        Actuator actuator = actuatorRepository.findById(actuatorId).orElseThrow(() -> new RuntimeException("Actuator not found"));
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

    public void updateMeasurementInterval(Long sensorId, int interval) {
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor not found"));
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