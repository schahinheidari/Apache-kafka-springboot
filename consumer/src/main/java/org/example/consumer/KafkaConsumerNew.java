package org.example.consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumerNew {
    @Bean
    public Consumer<RiderLocation> processRiderLocation() {
        return riderLocation -> {
            System.out.println("Received Rider Location: " + riderLocation.getRiderId()
            + " @ " + riderLocation.getLatitude() + " , " + riderLocation.getLongitude());
        };
    }
}
