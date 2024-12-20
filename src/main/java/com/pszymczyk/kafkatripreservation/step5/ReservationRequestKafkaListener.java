package com.pszymczyk.kafkatripreservation.step5;

import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import com.pszymczyk.kafkatripreservation.reservations.ReservationsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

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
    public void handleReservationRequest(ConsumerRecord<String, ReservationRequest> reservationRequest) {
        reservationsService.book(
                        new String(reservationRequest.headers().lastHeader("Request-Id").value(), StandardCharsets.UTF_8),
                        reservationRequest.value().userId(),
                        reservationRequest.value().tripCode())
                .ifPresent(reservationSummary -> log.info("Reservation summary {}.", reservationSummary));
    }
}
