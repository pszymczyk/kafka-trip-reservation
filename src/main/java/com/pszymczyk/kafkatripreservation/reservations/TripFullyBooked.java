package com.pszymczyk.kafkatripreservation.reservations;

public class TripFullyBooked extends RuntimeException {

    TripFullyBooked(String tripCode) {
        super("Trip " + tripCode + "fully booked.");
    }
}
