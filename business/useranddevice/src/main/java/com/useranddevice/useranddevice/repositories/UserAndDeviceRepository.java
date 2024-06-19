package com.useranddevice.useranddevice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.useranddevice.useranddevice.entities.UserAndDevice;

public interface UserAndDeviceRepository extends JpaRepository<UserAndDevice, Long> {
  List<UserAndDevice> findAllByUserId(Long userId);
  void deleteByUserIdAndDeviceId(Long userId, String deviceId);
  boolean existsByUserIdAndDeviceId(Long userId, String deviceId);
}