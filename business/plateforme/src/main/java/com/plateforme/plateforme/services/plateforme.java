package com.plateforme.plateforme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateforme.plateforme.config.MqttGateway;

@Service
public class plateforme {
   @Autowired
    private MqttGateway mqttGateway;

}
