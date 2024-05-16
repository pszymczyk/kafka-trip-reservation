package com.pszymczyk.kafkatripreservation.catalogue;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OutboxEntityCrudRepository extends CrudRepository<OutboxEntity, Long> {
    List<OutboxEntity> findAllByTopicPartitionOffsetIsNull();
}
