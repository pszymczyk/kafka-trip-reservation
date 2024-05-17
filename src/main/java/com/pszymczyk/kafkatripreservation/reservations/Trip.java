package com.pszymczyk.kafkatripreservation.reservations;

import java.util.*;

public class Trip {

    private final String tripCode;
    private final int seatsNumber;

    private final Set<String> reservationRequestsIds;
    private final List<Reservation> reservations;

    Trip(String tripCode, int seatsNumber) {
        this(tripCode, seatsNumber, new ArrayList<>(), new HashSet<>());
    }

    Trip(String tripCode, int seatsNumber, List<Reservation> reservations, Set<String> reservationRequestsIds) {
        this.tripCode = tripCode;
        this.seatsNumber = seatsNumber;
        this.reservations = reservations;
        this.reservationRequestsIds = reservationRequestsIds;
    }

    Optional<ReservationSummary> requestReservation(String reservationRequestId, String userId) {
        if (!hasFreeSeats()) {
            return Optional.empty();
        }

        Reservation newReservation = new Reservation(userId);
        reservations.add(newReservation);
        reservationRequestsIds.add(reservationRequestId);
        return Optional.of(new ReservationSummary(newReservation.getId().toString(), newReservation.getStatus().name()));
    }

    private boolean hasFreeSeats() {
        List<Reservation> reservations = this.reservations
                                             .stream()
                                             .filter(Reservation::isConfirmed)
                                             .toList();

        return seatsNumber > reservations.size();
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

    public boolean alreadyProcessed(String reservationRequestId) {
        return reservationRequestsIds.contains(reservationRequestId);
    }

    public Set<String> getReservationRequestsIds() {
        return reservationRequestsIds;
    }
}
