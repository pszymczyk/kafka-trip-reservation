package com.pszymczyk.kafkatripreservation.step4;

import com.pszymczyk.kafkatripreservation.ReservationsModuleProperties;
import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import com.pszymczyk.kafkatripreservation.step2.ReconcileJob;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ReservationsRestController {

    public record Request(
            String tripCode,
            String userId) {
    }

    private static final Logger log = LoggerFactory.getLogger(ReservationsRestController.class);

    private final ReservationsModuleProperties reservationsModuleProperties;
    private final KafkaOperations<String, ReservationRequest> kafkaOps;

    public ReservationsRestController(ReservationsModuleProperties reservationsModuleProperties, KafkaOperations<String, ReservationRequest> kafkaOps) {
        this.reservationsModuleProperties = reservationsModuleProperties;
        this.kafkaOps = kafkaOps;
    }

    @PostMapping(path = "/reservations", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void bookTrip(@RequestBody Request request, @RequestHeader("Request-Id") String requestId) throws ExecutionException, InterruptedException {
        ProducerRecord<String, ReservationRequest> producerRecord = new ProducerRecord<>(
                reservationsModuleProperties.getReservationsRequestsTopic(),
                null,
                request.tripCode,
                new ReservationRequest("reservation-request", request.tripCode, request.userId),
                List.of(new RecordHeader("Request-Id", requestId.getBytes(StandardCharsets.UTF_8)))
        );

        SendResult<String, ReservationRequest> sendResult = kafkaOps.send(producerRecord).get();
        log.info("Reservation request sent to kafka with metadata: {}", sendResult.getRecordMetadata());
    }
}
