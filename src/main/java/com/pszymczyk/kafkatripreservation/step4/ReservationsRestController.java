package com.pszymczyk.kafkatripreservation.step4;

import com.pszymczyk.kafkatripreservation.ReservationsModuleProperties;
import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class ReservationsRestController {

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


    public void bookTrip(@RequestBody Request request) throws ExecutionException, InterruptedException {
        //TODO
    }
}
