package com.pszymczyk.kafkatripreservation.catalogue;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OutboxEntity {

    @Id
    @GeneratedValue
    private long entityId;

    private String kafkaRecordKey;

    private byte[] kafkaRecordValue;

    private String topicPartitionOffset;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getKafkaRecordKey() {
        return kafkaRecordKey;
    }

    public void setKafkaRecordKey(String key) {
        this.kafkaRecordKey = key;
    }

    public byte[] getKafkaRecordValue() {
        return kafkaRecordValue;
    }

    public void setKafkaRecordValue(byte[] value) {
        this.kafkaRecordValue = value;
    }

    public String getTopicPartitionOffset() {
        return topicPartitionOffset;
    }

    public void setTopicPartitionOffset(String topicPartitionOffset) {
        this.topicPartitionOffset = topicPartitionOffset;
    }
}
