package org.example.producer.controller;

import lombok.RequiredArgsConstructor;
import org.example.producer.RiderLocation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * KafkaProducer is a REST controller that provides endpoints for producing messages
 * to Kafka topics using two different Kafka templates.
 *
 * One template handles plain text messages, while the other handles serialized
 * RiderLocation objects.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate1;
    private final KafkaTemplate<String, RiderLocation> kafkaTemplate2;

    /**
     * Sends a plain text message to the Kafka topic "my-topic".
     *
     * @param message the message to send
     * @return a confirmation message
     */
    @PostMapping("/send1")
    public String sendMessage1(@RequestParam("message") String message) {
        kafkaTemplate1.send("my-topic", message);
        return "Message sent: " + message;
    }

    /**
     * Sends a RiderLocation object to the Kafka topic "my-topic-new".
     * The RiderLocation is created with hardcoded values for demonstration.
     *
     * @param message an unused input (could be used to parameterize the message)
     * @return a confirmation message containing the rider ID
     */
    @PostMapping("/send2")
    public String sendMessage2(@RequestParam("message") String message) {
        RiderLocation location = new RiderLocation("rider123", 28.31, 77.34);
        kafkaTemplate2.send("my-topic-new", location);
        return "Message sent: " + location.getRiderId();
    }
}
