package com.actuator.actuator.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actuator.actuator.entities.IrrigationLog;
import com.actuator.actuator.repositories.IrrigationLogRepository;

@Service
public class IrrigationLogService {

    @Autowired
    private IrrigationLogRepository irrigationLogRepository;

    public IrrigationLog saveIrrigationLog(IrrigationLog log) {
        return irrigationLogRepository.save(log);
    }

    public List<IrrigationLog> getLogsByActuator(String actuatorId) {
        return irrigationLogRepository.findByActuatorId(actuatorId);
    }

    public void deleteIrrigationLog(String logId) {
        irrigationLogRepository.deleteById(logId);
    }
}
