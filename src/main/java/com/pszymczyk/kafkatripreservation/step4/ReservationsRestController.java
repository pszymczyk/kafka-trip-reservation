package com.pszymczyk.kafkatripreservation.step4;

import com.pszymczyk.kafkatripreservation.ReservationsModuleProperties;
import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class ReservationsRestController {

    private static final Logger log = LoggerFactory.getLogger(ReservationsRestController.class);

    public record Request(
            String tripCode,
            String userId) {
    }

    private final ReservationsModuleProperties reservationsModuleProperties;
    private final KafkaOperations<String, ReservationRequest> kafkaOps;

    public ReservationsRestController(ReservationsModuleProperties reservationsModuleProperties, KafkaOperations<String, ReservationRequest> kafkaOps) {
        this.reservationsModuleProperties = reservationsModuleProperties;
        this.kafkaOps = kafkaOps;
    }

    @PostMapping(path = "/reservations", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void bookTrip(@RequestBody Request request) throws ExecutionException, InterruptedException {
        kafkaOps.send(reservationsModuleProperties.getReservationsRequestsTopic(), request.tripCode, new ReservationRequest("reservation-request", request.tripCode, request.userId))
                .get();
    }
}
