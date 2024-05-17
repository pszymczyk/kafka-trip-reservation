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


    public RetryTopicConfiguration myOtherRetryTopic(KafkaTemplate<String, String> template,
                                                     ReservationsModuleProperties reservationsModuleProperties) {
        //TODO
        return null;
    }
}
