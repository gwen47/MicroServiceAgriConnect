package com.sensor.sensor.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensor.sensor.entities.SensorLog;
import com.sensor.sensor.repositories.SensorLogRepository;

@Service
public class SensorLogService {

    @Autowired
    private SensorLogRepository sensorLogRepository;

    public void logSensorReading(String sensorCode, double temperature, double humidity) {
        SensorLog log = new SensorLog(sensorCode, temperature, humidity, LocalDateTime.now());
        sensorLogRepository.save(log);
    }

    public List<SensorLog> getSensorLogsByCode(String code) {
        return sensorLogRepository.findAllBySensorCode(code);
    }
}
