package com.example.asyncevents.config;

// import com.example.asyncevents.event.TaskCreatedEvent;
import com.example.task_contracts.event.TaskCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

        @Bean
        public ConsumerFactory<String, TaskCreatedEvent> consumerFactory() {

                Map<String, Object> config = new HashMap<>();

                config.put(
                                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                "localhost:9092");

                config.put(
                                ConsumerConfig.GROUP_ID_CONFIG,
                                "task-group");

                config.put(
                                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                StringDeserializer.class);

                config.put(
                                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                JsonDeserializer.class);

                config.put(
                                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                                "earliest");

                JsonDeserializer<TaskCreatedEvent> deserializer = new JsonDeserializer<>(TaskCreatedEvent.class);

                deserializer.addTrustedPackages("*");

                return new DefaultKafkaConsumerFactory<>(
                                config,
                                new StringDeserializer(),
                                deserializer);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent> kafkaListenerContainerFactory() {

                ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

                factory.setConsumerFactory(
                                consumerFactory());
                // Day 12 - Multiple Consumers
                factory.setConcurrency(3);
                return factory;
        }
}