package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.SecurityEventEntity;
import com.holdings.scoring.domain.model.enums.SecurityEventType;
import com.holdings.scoring.domain.ports.out.SecurityEventRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class SecurityEventRepository implements SecurityEventRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<SecurityEventEntity> save(SecurityEventEntity event) {
        return databaseClient.sql("""
                        INSERT INTO security_events (id, event_type, description, ip_address, created_at, updated_at)
                        VALUES (:id, :eventType, :description, :ipAddress, :createdAt, :createdAt)
                        """)
                .bind("id", event.id())
                .bind("eventType", event.eventType().name())
                .bind("description", event.description())
                .bind("ipAddress", event.ipAddress())
                .bind("createdAt", event.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(event)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<SecurityEventEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM security_events WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<SecurityEventEntity>> findByEventType(SecurityEventType eventType) {
        return databaseClient.sql("SELECT * FROM security_events WHERE event_type = :eventType ORDER BY created_at DESC")
                .bind("eventType", eventType.name())
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<SecurityEventEntity>> findByIpAddress(String ipAddress) {
        return databaseClient.sql("SELECT * FROM security_events WHERE ip_address = :ipAddress ORDER BY created_at DESC")
                .bind("ipAddress", ipAddress)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM security_events WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private SecurityEventEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new SecurityEventEntity(
                row.get("id", UUID.class),
                SecurityEventType.valueOf(row.get("event_type", String.class)),
                row.get("description", String.class),
                row.get("ip_address", String.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
