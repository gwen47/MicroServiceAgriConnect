package com.useranddevice.useranddevice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.useranddevice.useranddevice.entities.UserAndDevice;
import com.useranddevice.useranddevice.services.UserAndDeviceService;

@RestController
@RequestMapping("/api/user-devices")
public class UserAndDeviceController {

    @Autowired
    private UserAndDeviceService userAndDeviceService;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<?> createUserAndDevice(@RequestBody UserAndDevice userAndDevice) {
        // si l'association existe déjà on ne la recrée pas
        if (userAndDeviceService.getUserAndDevicesByUserId(userAndDevice.getUserId(), userAndDevice.getDeviceId())) {
            return ResponseEntity.badRequest().body("User with id " + userAndDevice.getUserId() + " is already associated with device with code " + userAndDevice.getDeviceId());
        }

        String url = "";
        if (userAndDevice.getType().equals("sensor") ) {
            url = "http://localhost:8090/api/sensors/" + userAndDevice.getDeviceId();
        } else {
            url = "http://localhost:9090/api/actuators/" + userAndDevice.getDeviceId();
        }

        System.out.println(url);

        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                UserAndDevice newUserAndDevice = userAndDeviceService.createUserAndDevice(userAndDevice);
                return ResponseEntity.ok(newUserAndDevice);
            } else {
                return ResponseEntity.badRequest().body("Device with code " + userAndDevice.getDeviceId() + " does not exist.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to communicate with the device service: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserAndDevice>> getAllUserAndDevices() {
        List<UserAndDevice> userAndDevices = userAndDeviceService.getAllUserAndDevices();
        return ResponseEntity.ok(userAndDevices);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAndDevice>> getUserAndDevicesByUserId(@PathVariable Long userId) {
        List<UserAndDevice> userAndDevices = userAndDeviceService.getUserAndDevicesByUserId(userId);
        return ResponseEntity.ok(userAndDevices);
    }

    @DeleteMapping("/user/{userId}/device/{deviceId}")
    public ResponseEntity<Void> deleteUserAndDevice(@PathVariable Long userId, @PathVariable String deviceId) {
        userAndDeviceService.deleteUserAndDevice(userId, deviceId);
        return ResponseEntity.ok().build();
    }
}