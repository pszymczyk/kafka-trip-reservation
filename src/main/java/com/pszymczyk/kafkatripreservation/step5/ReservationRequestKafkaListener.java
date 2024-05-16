package com.pszymczyk.kafkatripreservation.step5;

import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import com.pszymczyk.kafkatripreservation.reservations.ReservationSummary;
import com.pszymczyk.kafkatripreservation.reservations.ReservationsService;
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

    @KafkaListener(
            groupId = "reservations-module-reservations-requests-listener",
            topics = "${reservations.reservations-requests-topic}",
            containerFactory = "reservationRequestEventListenerContainerFactory")
    public void handleReservationRequest(ReservationRequest reservationRequest) {
        ReservationSummary reservationSummary = reservationsService.book(reservationRequest.userId(),  reservationRequest.tripCode());
        log.info("Reservation summary {}.", reservationSummary);
    }


}
