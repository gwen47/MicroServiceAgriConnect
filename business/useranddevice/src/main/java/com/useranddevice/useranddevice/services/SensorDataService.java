package com.useranddevice.useranddevice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SensorDataService {
    private final RestTemplate restTemplate;

    @Value("${sensors-url}")
    private String sensorsURL;

    @Autowired
    public SensorDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double calculateAverageHumidity(String deviceId) {
        // TODO we should use FeignClient here
        String url = sensorsURL + deviceId + "/humidity";
        ResponseEntity<Double[]> response = restTemplate.getForEntity(url, Double[].class);
        Double[] humidities = response.getBody();
        return Arrays.stream(humidities).mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
    }

    public Double calculateAverageTemperature(String deviceId) {
        // TODO we should use FeignClient here
        String url = sensorsURL + deviceId + "/temperature";
        ResponseEntity<Double[]> response = restTemplate.getForEntity(url, Double[].class);
        Double[] temperatures = response.getBody();
        return Arrays.stream(temperatures).mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
    }
}
