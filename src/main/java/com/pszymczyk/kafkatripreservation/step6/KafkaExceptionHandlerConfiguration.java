package com.pszymczyk.kafkatripreservation.step6;

import org.springframework.stereotype.Component;

@Component
public class KafkaExceptionHandlerConfiguration {

//    @Bean
//    public DefaultErrorHandler errorHandler(KafkaTemplate<String, String> kafkaTemplate) {
//        BackOff fixedBackOff = new FixedBackOff(Duration.ofSeconds(10).toMillis(), 6);
//        DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(deadLetterPublishingRecoverer, fixedBackOff);
////        errorHandler.addRetryableExceptions(TripNotFound.class);
////        errorHandler.addNotRetryableExceptions(TripFullyBooked.class);
//        return errorHandler;
//    }
}
