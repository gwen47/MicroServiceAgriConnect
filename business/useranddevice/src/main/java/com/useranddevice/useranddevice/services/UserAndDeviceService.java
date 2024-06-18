package com.useranddevice.useranddevice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.useranddevice.useranddevice.entities.UserAndDevice;
import com.useranddevice.useranddevice.repositories.UserAndDeviceRepository;

import jakarta.transaction.Transactional;

@Service
public class UserAndDeviceService {

    @Autowired
    private UserAndDeviceRepository userAndDeviceRepository;

    public UserAndDevice createUserAndDevice(UserAndDevice userAndDevice) {
        return userAndDeviceRepository.save(userAndDevice);
    }

    public List<UserAndDevice> getAllUserAndDevices() {
        return userAndDeviceRepository.findAll();
    }

    public List<UserAndDevice> getUserAndDevicesByUserId(Long userId) {
        return userAndDeviceRepository.findAllByUserId(userId);
    }

    public boolean getUserAndDevicesByUserId(Long userId, String deviceId) {
        return userAndDeviceRepository.existsByUserIdAndDeviceId(userId, deviceId);
    }

    @Transactional
    public void deleteUserAndDevice(Long userId, String deviceId) {
        userAndDeviceRepository.deleteByUserIdAndDeviceId(userId, deviceId);
    }
}