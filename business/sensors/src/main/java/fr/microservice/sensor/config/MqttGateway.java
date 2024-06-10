package fr.microservice.sensor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqttGateway {

    @Autowired
    private MessageChannel mqttOutboundChannel;

    public void sendToMqtt(String sensorCode, int interval) {
        String topic = "sensor/interval";
        String payload = "{\"sensorCode\": \"" + sensorCode + "\", \"interval\": " + interval + "}";

        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, topic)
                .build();

        mqttOutboundChannel.send(message);
    }
}
