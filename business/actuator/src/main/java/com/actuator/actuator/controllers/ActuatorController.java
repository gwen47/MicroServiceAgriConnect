package com.actuator.actuator.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.actuator.actuator.entities.Actuator;
import com.actuator.actuator.entities.IrrigationLog;
import com.actuator.actuator.services.ActuatorService;
import com.actuator.actuator.services.IrrigationLogService;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorController {

    @Autowired
    private ActuatorService actuatorService;

    @Autowired
    private IrrigationLogService irrigationLogService;

    @PostMapping
    public ResponseEntity<Actuator> createActuator(@RequestBody Actuator actuator) {
        if (actuatorService.getActuatorByCode(actuator.getCode()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();  // Return 409 Conflict if actuator code already exists
        }
        Actuator newActuator = actuatorService.saveActuator(actuator);
        return ResponseEntity.ok(newActuator);
    }

    @GetMapping
    public List<Actuator> getAllActuators() {
        return actuatorService.getAllActuators();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Actuator> getActuatorByCode(@PathVariable String code) {
        return actuatorService.getActuatorByCode(code)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // obtenir l'état d'arrosage d'un actuateur
    @GetMapping("/{code}/irrigation-status")
    public ResponseEntity<String> getIrrigationStatus(@PathVariable String code) {
        Actuator actuator = actuatorService.getActuatorByCode(code)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actuator not found"));

        // calcule le temps restant d'arrosage avec la durée d'arrosage présent dans les logs d'irrigation
        // il faut récupérer le dernier log 
        List<IrrigationLog> logs = irrigationLogService.getLogsByActuator(code);
        long remainingTime = 0;
        if (!logs.isEmpty()) {
            IrrigationLog lastLog = logs.get(logs.size() - 1);
            LocalDateTime startTime = lastLog.getStartTime();
            LocalDateTime endTime = lastLog.getEndTime();
            if (endTime == null) {
                Duration duration = Duration.between(startTime, LocalDateTime.now());
                remainingTime = actuator.getIrrigationDuration() - duration.getSeconds();
            }
        }
        // retourner la valeur de l'état d'arrosage de l'actuateur
        if (actuator.isIrrigating()) {
            return ResponseEntity.ok("Irrigating for " + remainingTime + " seconds");
        } else {
            return ResponseEntity.ok("Not irrigating");
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteActuatorByCode(@PathVariable String code) {
        actuatorService.deleteActuatorByCode(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{actuatorId}/start-irrigation")
    public ResponseEntity<Void> startIrrigation(@PathVariable String actuatorId, @RequestParam long duration) {
        if (duration < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect value, duration must be positive or equal to zero");
        }
        Actuator actuator = actuatorService.getActuatorByCode(actuatorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actuator not found"));
        IrrigationLog log = new IrrigationLog();
        log.setActuatorId(actuatorId);
        LocalDateTime localTime = LocalDateTime.now();
        log.setStartTime(localTime);
        log.setStatus("started");
        irrigationLogService.saveIrrigationLog(log);
        actuator.setIrrigating(true);
        actuator.setIrrigationDuration(duration);
        actuatorService.saveActuator(actuator);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{actuatorId}/stop-irrigation")
    public ResponseEntity<Void> stopIrrigation(@PathVariable String actuatorId) {
        List<IrrigationLog> logs = irrigationLogService.getLogsByActuator(actuatorId);
        if (!logs.isEmpty()) {
            IrrigationLog lastLog = logs.get(logs.size() - 1);
            lastLog.setEndTime(LocalDateTime.now());
            lastLog.setStatus("ended");
            irrigationLogService.saveIrrigationLog(lastLog);
        }

        Actuator actuator = actuatorService.getActuatorByCode(actuatorId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actuator not found"));
        actuator.setIrrigating(false);
        actuator.setIrrigationDuration(0);
        actuatorService.saveActuator(actuator);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{actuatorId}/logs")
    public List<IrrigationLog> getIrrigationLogsByActuator(@PathVariable String actuatorId) {
        return irrigationLogService.getLogsByActuator(actuatorId);
    }

}
