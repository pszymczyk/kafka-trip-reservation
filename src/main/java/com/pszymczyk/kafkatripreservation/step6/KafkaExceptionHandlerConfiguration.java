package com.pszymczyk.kafkatripreservation.step6;

import com.pszymczyk.kafkatripreservation.ReservationsModuleProperties;
import com.pszymczyk.kafkatripreservation.reservations.TripNotFound;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class KafkaExceptionHandlerConfiguration {

    @Bean
    public RetryTopicConfiguration myOtherRetryTopic(KafkaTemplate<String, String> template,
                                                     ReservationsModuleProperties reservationsModuleProperties) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .includeTopics(List.of(reservationsModuleProperties.getReservationsRequestsTopic()))
                .exponentialBackoff(Duration.ofSeconds(30).toMillis(), 2, Duration.ofDays(1).toMillis())
                .maxAttempts(10)
                .retryOn(TripNotFound.class)
                .create(template);
    }
}
