package com.pszymczyk.kafkatripreservation.step1;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaOperationsConfig {

    @Bean
    public KafkaOperations<String, byte[]> kafkaOperations(KafkaProperties kafkaProperties) {
        DefaultKafkaProducerFactory<String, byte[]> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(null));
        defaultKafkaProducerFactory.setKeySerializer(new StringSerializer());
        defaultKafkaProducerFactory.setValueSerializer(new ByteArraySerializer());
        return new KafkaTemplate<>(defaultKafkaProducerFactory);
    }
}
