package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.DelinquencyEntity;
import com.holdings.scoring.domain.ports.out.DelinquencyRepositoryPort;
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
public class DelinquencyRepository implements DelinquencyRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<DelinquencyEntity> save(DelinquencyEntity delinquency) {
        return databaseClient.sql("""
                        INSERT INTO delinquencies (id, credit_account_id, days_past_due, amount, reported_at, created_at, updated_at)
                        VALUES (:id, :creditAccountId, :daysPastDue, :amount, :reportedAt, :reportedAt, :reportedAt)
                        """)
                .bind("id", delinquency.id())
                .bind("creditAccountId", delinquency.creditAccountId())
                .bind("daysPastDue", delinquency.daysPastDue())
                .bind("amount", delinquency.amount())
                .bind("reportedAt", delinquency.reportedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(delinquency)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<DelinquencyEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM delinquencies WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<DelinquencyEntity>> findByCreditAccountId(UUID creditAccountId) {
        return databaseClient.sql("SELECT * FROM delinquencies WHERE credit_account_id = :creditAccountId ORDER BY reported_at DESC")
                .bind("creditAccountId", creditAccountId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM delinquencies WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private DelinquencyEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new DelinquencyEntity(
                row.get("id", UUID.class),
                row.get("credit_account_id", UUID.class),
                row.get("days_past_due", Integer.class),
                row.get("amount", BigDecimal.class),
                row.get("reported_at", LocalDateTime.class)
        );
    }
}
