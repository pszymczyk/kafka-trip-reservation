package com.pszymczyk.kafkatripreservation.step1;


import com.pszymczyk.kafkatripreservation.CatalogueModuleProperties;
import com.pszymczyk.kafkatripreservation.catalogue.OutboxEntity;
import com.pszymczyk.kafkatripreservation.catalogue.OutboxEntityCreated;
import com.pszymczyk.kafkatripreservation.catalogue.OutboxEntityCrudRepository;
import com.pszymczyk.kafkatripreservation.step2.ReconcileJob;
import com.pszymczyk.kafkatripreservation.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OutboxEntityPostPersistListener {

    private static final Logger log = LoggerFactory.getLogger(OutboxEntityPostPersistListener.class);

    private final KafkaOperations<String, byte[]> kafkaOps;
    private final OutboxEntityCrudRepository outboxEntityCrudRepository;
    private final CatalogueModuleProperties catalogueModuleProperties;

    public OutboxEntityPostPersistListener(KafkaOperations<String, byte[]> kafkaOps, OutboxEntityCrudRepository outboxEntityCrudRepository, CatalogueModuleProperties catalogueModuleProperties) {
        this.kafkaOps = kafkaOps;
        this.outboxEntityCrudRepository = outboxEntityCrudRepository;
        this.catalogueModuleProperties = catalogueModuleProperties;
    }

    /**
     * Try to send event to kafka topic just after database transaction flushed.
     * When method failed for any reason #{@link ReconcileJob} will reconcile.
     */
    @Async
    @TransactionalEventListener
    void handleOutboxEntityCreated(OutboxEntityCreated outboxEntityCreated) {
        kafkaOps.send(catalogueModuleProperties.getTripEventsTopic(), outboxEntityCreated.outboxEntity().getKafkaRecordKey(), outboxEntityCreated.outboxEntity().getKafkaRecordValue())
                .whenCompleteAsync((sendResult, throwable) -> {
                    if (throwable != null) {
                        log.warn("Error sending event after outbox entity created.", throwable);
                    }

                    OutboxEntity outboxEntityInSession = outboxEntityCrudRepository.findById(outboxEntityCreated.outboxEntity().getEntityId()).orElseThrow();
                    outboxEntityInSession.setTopicPartitionOffset(String.format("%s,%s,%s",
                            sendResult.getRecordMetadata().topic(),
                            sendResult.getRecordMetadata().partition(),
                            sendResult.getRecordMetadata().offset()));

                    Utils.failSometimes();

                    outboxEntityCrudRepository.save(outboxEntityInSession);
                });
    }
}
