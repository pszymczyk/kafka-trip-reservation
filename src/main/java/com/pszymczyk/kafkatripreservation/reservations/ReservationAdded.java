package com.pszymczyk.kafkatripreservation.reservations;

public class ReservationAdded {

    private final String tripCode;
    private final String reservationId;
    private final String userId;
    private final String status;

    ReservationAdded(String tripCode, String reservationId, String userId, String status) {
        this.tripCode = tripCode;
        this.reservationId = reservationId;
        this.userId = userId;
        this.status = status;
    }

    String getTripCode() {
        return tripCode;
    }

    String getReservationId() {
        return reservationId;
    }

    String getUserId() {
        return userId;
    }

    String getStatus() {
        return status;
    }
}
