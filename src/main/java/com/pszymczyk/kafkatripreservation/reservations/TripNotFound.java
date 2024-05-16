package com.pszymczyk.kafkatripreservation.reservations;

public class TripNotFound extends RuntimeException {

    TripNotFound(String tripCode) {
        super("Trip with id " + tripCode + " not found.");
    }
}
