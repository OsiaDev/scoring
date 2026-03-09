package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.ConsentHistoryEntity;
import com.holdings.scoring.domain.ports.out.ConsentHistoryRepositoryPort;
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
public class ConsentHistoryRepository implements ConsentHistoryRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<ConsentHistoryEntity> save(ConsentHistoryEntity history) {
        return databaseClient.sql("""
                        INSERT INTO consent_history (id, consent_id, status, reason, created_at, updated_at)
                        VALUES (:id, :consentId, :status, :reason, :createdAt, :createdAt)
                        """)
                .bind("id", history.id())
                .bind("consentId", history.consentId())
                .bind("status", history.status())
                .bind("reason", history.reason())
                .bind("createdAt", history.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(history)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ConsentHistoryEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM consent_history WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ConsentHistoryEntity>> findByConsentId(UUID consentId) {
        return databaseClient.sql("SELECT * FROM consent_history WHERE consent_id = :consentId ORDER BY created_at DESC")
                .bind("consentId", consentId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM consent_history WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private ConsentHistoryEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new ConsentHistoryEntity(
                row.get("id", UUID.class),
                row.get("consent_id", UUID.class),
                row.get("status", Boolean.class),
                row.get("reason", String.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
