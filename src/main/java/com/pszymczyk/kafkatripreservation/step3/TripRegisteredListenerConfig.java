package com.pszymczyk.kafkatripreservation.step3;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class TripRegisteredListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TripRegisteredEvent> tripRegisteredEventListenerContainerFactory(
            KafkaProperties kafkaProperties) {

        DefaultKafkaConsumerFactory<String, TripRegisteredEvent> cf = new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(null));
        cf.setKeyDeserializer(new StringDeserializer());
        JsonDeserializer<TripRegisteredEvent> valueDeserializer = new JsonDeserializer<>(TripRegisteredEvent.class);
        valueDeserializer.setUseTypeHeaders(false);
        cf.setValueDeserializer(valueDeserializer);
        ConcurrentKafkaListenerContainerFactory<String, TripRegisteredEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);

        return factory;
    }
}
