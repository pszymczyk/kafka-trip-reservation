package com.pszymczyk.kafkatripreservation.reservations;

import org.springframework.data.repository.CrudRepository;

public interface CrudTripRepository extends CrudRepository<TripEntity, Long> {

    TripEntity findByTripCode(String tripCode);

}
