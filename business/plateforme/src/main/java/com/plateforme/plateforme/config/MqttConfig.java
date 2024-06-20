package com.plateforme.plateforme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MqttConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConfig.class);

    @Bean
    MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1883"});
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    MqttPahoMessageDrivenChannelAdapter inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("sensorClient", mqttClientFactory(), "sensor/data");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setErrorChannel(errorChannel());
        return adapter;
    }

    @Bean
    MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("springClient", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("sensor/interval");
        return messageHandler;
    }

    @Bean
    MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    MessageHandler handler() {
        return message -> {
            try {
                String payload = (String) message.getPayload();
                LOGGER.info("Received message: {}", payload);
                // Add code to update your database here
            } catch (Exception e) {
                LOGGER.error("Error handling message", e);
                throw new MessagingException("Error handling message", e);
            }
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "errorChannel")
    MessageHandler errorHandler() {
        return message -> {
            ErrorMessage errorMessage = (ErrorMessage) message;
            Throwable payload = errorMessage.getPayload();
            LOGGER.error("Error occurred in MQTT handling: {}", payload.getMessage(), payload);
        };
    }
}