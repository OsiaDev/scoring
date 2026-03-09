package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.CreditAccountHistoryEntity;
import com.holdings.scoring.domain.ports.out.CreditAccountHistoryRepositoryPort;
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
public class CreditAccountHistoryRepository implements CreditAccountHistoryRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<CreditAccountHistoryEntity> save(CreditAccountHistoryEntity history) {
        return databaseClient.sql("""
                        INSERT INTO credit_account_history (id, credit_account_id, balance, days_past_due, status, reported_at, created_at, updated_at)
                        VALUES (:id, :creditAccountId, :balance, :daysPastDue, :status, :reportedAt, :reportedAt, :reportedAt)
                        """)
                .bind("id", history.id())
                .bind("creditAccountId", history.creditAccountId())
                .bind("balance", history.balance())
                .bind("daysPastDue", history.daysPastDue())
                .bind("status", history.status())
                .bind("reportedAt", history.reportedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(history)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CreditAccountHistoryEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM credit_account_history WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditAccountHistoryEntity>> findByCreditAccountId(UUID creditAccountId) {
        return databaseClient.sql("SELECT * FROM credit_account_history WHERE credit_account_id = :creditAccountId ORDER BY reported_at DESC")
                .bind("creditAccountId", creditAccountId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM credit_account_history WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private CreditAccountHistoryEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new CreditAccountHistoryEntity(
                row.get("id", UUID.class),
                row.get("credit_account_id", UUID.class),
                row.get("balance", BigDecimal.class),
                row.get("days_past_due", Integer.class),
                row.get("status", String.class),
                row.get("reported_at", LocalDateTime.class)
        );
    }
}
