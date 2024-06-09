package fr.microservice.sensor.model;

import lombok.NonNull;

public class MqttPublishModel {

    @NonNull
    private String topic;

    @NonNull
    private String message;

    @NonNull
    private Boolean retained;

    @NonNull
    private Integer qos;


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRetained() {
        return retained;
    }

    public void setRetained(Boolean retained) {
        this.retained = retained;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }
}
