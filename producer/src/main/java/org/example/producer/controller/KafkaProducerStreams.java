package org.example.producer.controller;


import org.example.producer.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {
    @Bean
    public Supplier<RiderLocation> sendRiderLocation() {
        return () -> {
            RiderLocation riderLocation = new RiderLocation("rider123", 16.27, 68.45);
            System.out.println("Sending rider location: " + riderLocation.getRiderId());
            return riderLocation;
        };
    }


}

