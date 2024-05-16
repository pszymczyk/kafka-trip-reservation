package com.pszymczyk.kafkatripreservation.reservations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Trip {

    private final String tripCode;
    private final int seatsNumber;

    private List<Reservation> reservations;

    Trip(String tripCode, int seatsNumber) {
        this(tripCode, seatsNumber, new ArrayList<>());
    }

    Trip(String tripCode, int seatsNumber, List<Reservation> reservations) {
        this.tripCode = tripCode;
        this.seatsNumber = seatsNumber;
        this.reservations = reservations;
    }

    Optional<ReservationSummary> requestReservation(String userId) {
        if (!hasFreeSeats()) {
            return Optional.empty();
        }

        Reservation newReservation = new Reservation(userId);
        addReservation(newReservation);
        return Optional.of(new ReservationSummary(newReservation.getId().toString(), newReservation.getStatus().name()));
    }

    private boolean hasFreeSeats() {
        List<Reservation> reservations = this.reservations
                                             .stream()
                                             .filter(Reservation::isConfirmed)
                                             .toList();

        return seatsNumber > reservations.size();
    }

    private void addReservation(Reservation newReservation) {
        List<Reservation> newReservations = new ArrayList<>(reservations);
        newReservations.add(newReservation);
        reservations = newReservations;
    }

    List<Reservation> getReservations() {
        return reservations;
    }

    String getTripCode() {
        return tripCode;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }
}
