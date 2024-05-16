package com.pszymczyk.kafkatripreservation.step3;


import com.pszymczyk.kafkatripreservation.reservations.ReservationsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TripRegisteredKafkaListener {

    private final ReservationsService reservationsService;

    public TripRegisteredKafkaListener(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @KafkaListener(topics = "${catalogue.trip-events-topic}",
            groupId = "reservations-module-trip-registered-listener",
            containerFactory = "tripRegisteredEventListenerContainerFactory")
    public void handleTripRegistered(TripRegisteredEvent tripRegisteredEvent) {
        reservationsService.registerTrip(tripRegisteredEvent.tripCode(), tripRegisteredEvent.seatsNumber());
    }
}
