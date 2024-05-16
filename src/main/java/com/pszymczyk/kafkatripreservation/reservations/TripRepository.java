package com.pszymczyk.kafkatripreservation.reservations;

public interface TripRepository {

    Trip findTrip(String tripCode);

    void save(Trip trip);
}
