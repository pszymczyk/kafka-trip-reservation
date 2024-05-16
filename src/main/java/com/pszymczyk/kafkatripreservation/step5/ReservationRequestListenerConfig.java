package com.pszymczyk.kafkatripreservation.step5;

import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class ReservationRequestListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservationRequest> reservationRequestEventListenerContainerFactory(
            KafkaProperties kafkaProperties) {

        DefaultKafkaConsumerFactory<String, ReservationRequest> cf = new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(null));
        cf.setKeyDeserializer(new StringDeserializer());
        JsonDeserializer<ReservationRequest> valueDeserializer = new JsonDeserializer<>(ReservationRequest.class);
        valueDeserializer.setUseTypeHeaders(false);
        cf.setValueDeserializer(valueDeserializer);
        ConcurrentKafkaListenerContainerFactory<String, ReservationRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);

        return factory;
    }
}
