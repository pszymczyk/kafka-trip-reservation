package com.pszymczyk.kafkatripreservation.catalogue;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "trips", collectionResourceRel = "trips")
public interface TripEntityPagingAndSortingRepository extends PagingAndSortingRepository<TripEntity, Long> {

}
