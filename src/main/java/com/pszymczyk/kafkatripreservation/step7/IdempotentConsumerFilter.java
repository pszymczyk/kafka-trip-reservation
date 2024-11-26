package com.pszymczyk.kafkatripreservation.step7;

import com.pszymczyk.kafkatripreservation.reservations.ReservationRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class IdempotentConsumerFilter implements RecordFilterStrategy<String, ReservationRequest> {

    private final EntityManager entityManager;

    public IdempotentConsumerFilter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean filter(ConsumerRecord<String, ReservationRequest> reservationRequest) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM RESERVATIONS_TRIP_ENTITY_RESERVATION_REQUESTS_IDS rri1_0 WHERE rri1_0.RESERVATION_REQUESTS_IDS=?");
        query.setParameter(1, new String(reservationRequest.headers().lastHeader("requestId").value(), StandardCharsets.UTF_8));
        int firstResult = ((Number) query.getSingleResult()).intValue();
        return firstResult > 0;
    }
}
