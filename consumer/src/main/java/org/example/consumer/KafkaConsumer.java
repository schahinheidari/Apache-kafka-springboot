package org.example.consumer;
/*
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

*//**
 * KafkaConsumer is a Spring-managed component responsible for listening
 * to messages from specified Kafka topics and processing them accordingly.
 *//*
@Component
public class KafkaConsumer {

    *//**
     * Listens for messages on the Kafka topic "my-topic" with consumer group "my-new-group".
     * This method prints the received message to the console with an identifier.
     *
     * @param message the message received from the Kafka topic
     *//*
    @KafkaListener(topics = "my-topic" , groupId = "my-new-group")
    public void listen1(String message) {
        System.out.println("Received Message 1: " + message);
    }

    *//**
     * Listens for messages on the Kafka topic "my-topic" with consumer group "my-new-group-2".
     * This method also prints the received message to the console, but belongs to a different consumer group.
     *
     * @param message the message received from the Kafka topic
     *//*
    @KafkaListener(topics = "my-topic" , groupId = "my-new-group-2")
    public void listen2(String message) {
        System.out.println("Received Message 2: " + message);
    }

    *//**
     * Listens for RiderLocation objects on the Kafka topic "my-topic-new" with consumer group "my-new-group-rider".
     * This method extracts and logs the rider's ID, latitude, and longitude.
     *
     * @param location the RiderLocation object received from the Kafka topic
     *//*
    @KafkaListener(topics = "my-topic-new" , groupId = "my-new-group-rider")
    public void listenRiderLocation(RiderLocation location) {
        System.out.println("Received Location: " + location.getRiderId() +
                " : " + location.getLatitude() + " : " + location.getLongitude());
    }
}*/
