package com.pszymczyk.kafkatripreservation.reservations;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReservationsService {

    private static final Logger log = LoggerFactory.getLogger(ReservationsService.class);

    private final TripRepository tripRepository;

    public ReservationsService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Transactional
    public Optional<ReservationSummary> book(String reservationRequestId, String userId, String tripCode) {
        Trip trip = tripRepository.findTrip(tripCode);

        if (trip == null) {
            log.warn("Trip with code {} not found. Retry mechanism will try to submit reservation again.", tripCode);
            throw new TripNotFound(tripCode);
        }

        return Optional.of(trip.requestReservation(reservationRequestId, userId)
                .map(summary -> {
                    tripRepository.save(trip);
                    return summary;
                })
                .orElseThrow(() -> new TripFullyBooked(tripCode)));
    }

    @Transactional
    public void registerTrip(String tripCode, int seatsNumber) {
        if (tripRepository.findTrip(tripCode) != null) {
            log.info("Trip with code {} already exists", tripCode);
            return;
        }

        Trip trip = new Trip(tripCode, seatsNumber);
        tripRepository.save(trip);
    }
}
