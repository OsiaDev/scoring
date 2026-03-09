package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.SecurityEventEntity;
import com.holdings.scoring.domain.model.enums.SecurityEventType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el registro y consulta de eventos de seguridad del sistema.
 */
public interface SecurityEventUseCase {

    CompletableFuture<SecurityEventEntity> registerEvent(SecurityEventEntity event);

    CompletableFuture<Optional<SecurityEventEntity>> findEventById(UUID id);

    CompletableFuture<List<SecurityEventEntity>> findEventsByType(SecurityEventType eventType);

    CompletableFuture<List<SecurityEventEntity>> findEventsByIpAddress(String ipAddress);

    CompletableFuture<Void> deleteEvent(UUID id);

}
