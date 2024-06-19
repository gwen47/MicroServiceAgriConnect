package com.useranddevice.useranddevice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class TransactionConfig {

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}