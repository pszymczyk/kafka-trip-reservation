package com.pszymczyk.kafkatripreservation.step5;

import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import com.pszymczyk.kafkatripreservation.reservations.ReservationsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationRequestKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(ReservationRequestKafkaListener.class);

    private final ReservationsService reservationsService;

    public ReservationRequestKafkaListener(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    public void handleReservationRequest(ConsumerRecord<String, ReservationRequest> reservationRequest) {
        //TODO
    }
}
