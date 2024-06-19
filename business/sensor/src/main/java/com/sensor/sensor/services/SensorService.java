package com.sensor.sensor.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensor.sensor.entities.Sensor;
import com.sensor.sensor.exceptions.ResourceNotFoundException;
import com.sensor.sensor.repositories.SensorRepo;

@Service
public class SensorService {

    @Autowired
    private SensorRepo sensorRepository;

    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getSensorById(String id) {
        return sensorRepository.findById(id);
    }

    public Optional<Sensor> getSensorByCode(String code) {
        return sensorRepository.findByCode(code);
    }

    public void deleteSensor(String id) {
        sensorRepository.deleteById(id);
    }

    public boolean existsByCode(String code) {
        return sensorRepository.existsByCode(code);
    }
    
    public Sensor updateSensor(String id, Sensor updatedSensor) {
        return sensorRepository.findById(id)
            .map(sensor -> {
                sensor.setCode(updatedSensor.getCode());
                sensor.setLatitude(updatedSensor.getLatitude());
                sensor.setLongitude(updatedSensor.getLongitude());
                sensor.setTemperature(updatedSensor.getTemperature());
                sensor.setHumidity(updatedSensor.getHumidity());
                sensor.setLastUpdated(updatedSensor.getLastUpdated());
                sensor.setMeasurementInterval(updatedSensor.getMeasurementInterval());
                return sensorRepository.save(sensor);
            })
            .orElseGet(() -> {
                updatedSensor.setId(id);
                return sensorRepository.save(updatedSensor);
            });
    }

    // mettre à jour l'intervalle de mesure pour tous les capteurs
    public void updateMeasurementIntervalForAll (int interval) {
        List<Sensor> sensors = sensorRepository.findAll();
        for (Sensor sensor : sensors) {
            sensor.setMeasurementInterval(interval);
            sensorRepository.save(sensor);
        }
    }
}