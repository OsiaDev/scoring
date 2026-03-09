package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.CollectionEntity;
import com.holdings.scoring.domain.ports.out.CollectionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class CollectionRepository implements CollectionRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<CollectionEntity> save(CollectionEntity collection) {
        return databaseClient.sql("""
                        INSERT INTO collections (id, credit_account_id, agency_name, amount, assigned_at, created_at, updated_at)
                        VALUES (:id, :creditAccountId, :agencyName, :amount, :assignedAt, :assignedAt, :assignedAt)
                        """)
                .bind("id", collection.id())
                .bind("creditAccountId", collection.creditAccountId())
                .bind("agencyName", collection.agencyName())
                .bind("amount", collection.amount())
                .bind("assignedAt", collection.assignedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(collection)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CollectionEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM collections WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CollectionEntity>> findByCreditAccountId(UUID creditAccountId) {
        return databaseClient.sql("SELECT * FROM collections WHERE credit_account_id = :creditAccountId ORDER BY assigned_at DESC")
                .bind("creditAccountId", creditAccountId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM collections WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private CollectionEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new CollectionEntity(
                row.get("id", UUID.class),
                row.get("credit_account_id", UUID.class),
                row.get("agency_name", String.class),
                row.get("amount", BigDecimal.class),
                row.get("assigned_at", LocalDateTime.class)
        );
    }
}
