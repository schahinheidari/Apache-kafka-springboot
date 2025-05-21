package org.example.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * KafkaTopicConfig is a configuration class that defines Kafka topic beans
 * to be automatically created on application startup.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a new Kafka topic named "my-new-topic-2" with 3 partitions and a replication factor of 1.
     * This topic will be automatically created if it doesn't already exist on the Kafka broker.
     *
     * @return a NewTopic object representing the topic configuration
     */
    @Bean
    public NewTopic createTopic() {
        return new NewTopic("my-new-topic-2", 3, (short) 1);
    }
}
