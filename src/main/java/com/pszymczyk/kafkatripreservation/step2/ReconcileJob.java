package com.pszymczyk.kafkatripreservation.step2;

import com.pszymczyk.kafkatripreservation.CatalogueModuleProperties;
import com.pszymczyk.kafkatripreservation.catalogue.OutboxEntityCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReconcileJob {

    private static final Logger log = LoggerFactory.getLogger(ReconcileJob.class);

    private final OutboxEntityCrudRepository outboxEntityCrudRepository;
    private final KafkaOperations<String, byte[]> kafkaOps;
    private final CatalogueModuleProperties catalogueModuleProperties;

    public ReconcileJob(OutboxEntityCrudRepository outboxEntityCrudRepository, KafkaOperations<String, byte[]> kafkaOps, CatalogueModuleProperties catalogueModuleProperties) {
        this.outboxEntityCrudRepository = outboxEntityCrudRepository;
        this.kafkaOps = kafkaOps;
        this.catalogueModuleProperties = catalogueModuleProperties;
    }

    @Scheduled(cron = "0 * * * * *")
    void reconcile() {
        log.info("Starting reconcile job.");
        outboxEntityCrudRepository.findAllByTopicPartitionOffsetIsNull()
                .forEach(outboxEntity -> {
                    log.info("Reconcile trip {}.", outboxEntity.getKafkaRecordKey());
                    kafkaOps.send(catalogueModuleProperties.getTripEventsTopic(), outboxEntity.getKafkaRecordKey(), outboxEntity.getKafkaRecordValue())
                            .whenCompleteAsync((sendResult, throwable) -> {
                                if (throwable != null) {
                                    log.warn("Error while sending event from outbox to kafka.", throwable);
                                }

                                outboxEntity.setTopicPartitionOffset(String.format("%s,%s,%s",
                                        sendResult.getRecordMetadata().topic(),
                                        sendResult.getRecordMetadata().partition(),
                                        sendResult.getRecordMetadata().offset()));

                                outboxEntityCrudRepository.save(outboxEntity);
                            });
                });
        log.info("Reconciliation finished.");
    }
}

