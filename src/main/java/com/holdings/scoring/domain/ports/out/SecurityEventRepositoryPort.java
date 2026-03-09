package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.SecurityEventEntity;
import com.holdings.scoring.domain.model.enums.SecurityEventType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SecurityEventRepositoryPort {

    CompletableFuture<SecurityEventEntity> save(SecurityEventEntity event);

    CompletableFuture<Optional<SecurityEventEntity>> findById(UUID id);

    CompletableFuture<List<SecurityEventEntity>> findByEventType(SecurityEventType eventType);

    CompletableFuture<List<SecurityEventEntity>> findByIpAddress(String ipAddress);

    CompletableFuture<Void> deleteById(UUID id);

}
