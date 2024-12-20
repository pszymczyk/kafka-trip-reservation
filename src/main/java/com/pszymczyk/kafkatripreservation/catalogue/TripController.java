package com.pszymczyk.kafkatripreservation.catalogue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@RestController
public class TripController {

    public record TripRegisteredEvent(String type, String tripCode, int seatsNumber) {

    }


    record CreateTripRequest(String description, String shortDescription, String destination, Integer seatsNumber,
                             String tripCode) {
    }


    private final TripEntityCrudRepository tripEntityCrudRepository;
    private final OutboxEntityCrudRepository outboxEntityCrudRepository;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TripController(TripEntityCrudRepository tripEntityCrudRepository,
                          OutboxEntityCrudRepository outboxEntityCrudRepository, ObjectMapper objectMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.tripEntityCrudRepository = tripEntityCrudRepository;
        this.outboxEntityCrudRepository = outboxEntityCrudRepository;
        this.objectMapper = objectMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping(path = "/trips", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    CreateTripRequest save(@RequestBody CreateTripRequest createTripRequest) throws JsonProcessingException {
        TripEntity catalogueEntity = new TripEntity();
        catalogueEntity.setDescription(createTripRequest.description);
        catalogueEntity.setShortDescription(createTripRequest.shortDescription);
        catalogueEntity.setDestination(createTripRequest.destination);
        catalogueEntity.setSeatsNumber(createTripRequest.seatsNumber);
        catalogueEntity.setTripCode(createTripRequest.tripCode);

        tripEntityCrudRepository.save(catalogueEntity);

        OutboxEntity outboxEntity = new OutboxEntity();
        outboxEntity.setKafkaRecordKey(createTripRequest.tripCode);
        outboxEntity.setKafkaRecordValue(objectMapper.writeValueAsBytes(new TripRegisteredEvent("trip-registered", createTripRequest.tripCode, createTripRequest.seatsNumber)));

        publishApplicationEvent(outboxEntityCrudRepository.save(outboxEntity));
        return createTripRequest;
    }

    private void publishApplicationEvent(OutboxEntity outboxEntity) {
        applicationEventPublisher.publishEvent(new OutboxEntityCreated(outboxEntity));
    }

    @GetMapping(path = "/trips", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> getAllTrips() {
        List<TripEntity> allTrips = StreamSupport.stream(tripEntityCrudRepository.findAll().spliterator(), false).toList();
        return Map.of("trips", allTrips);
    }

}
