package org.example.producer.controller;


import org.example.producer.RiderLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {
    @Bean
    public Supplier<RiderLocation> sendRiderLocation() {
        Random rand = new Random();
        return () -> {
            String riderId = "rider " + rand.nextInt(20);
            RiderLocation riderLocation = new RiderLocation(riderId,
                    16.27, 68.45);
            System.out.println("Sending rider location: " + riderLocation.getRiderId());
            return riderLocation;
        };
    }

    @Bean
    public Supplier<Message<String>> sendRiderStatus() {
        Random rand = new Random();
        return () -> {
            String riderId = "rider " + rand.nextInt(20);
            String status = rand.nextBoolean() ? "ride Started" : "ride Completed";
            System.out.println("Sending status: " + status);
            return MessageBuilder.withPayload(riderId + " : " + status)
                    .setHeader(KafkaHeaders.KEY, riderId.getBytes())
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
                    .build();
        };
    }


}

