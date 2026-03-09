package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.CreditScoreEntity;
import com.holdings.scoring.domain.ports.out.CreditScoreRepositoryPort;
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
public class CreditScoreRepository implements CreditScoreRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<CreditScoreEntity> save(CreditScoreEntity score) {
        return databaseClient.sql("""
                        INSERT INTO credit_scores (id, person_id, model_id, score, calculated_at, created_at, updated_at)
                        VALUES (:id, :personId, :modelId, :score, :calculatedAt, :calculatedAt, :calculatedAt)
                        """)
                .bind("id", score.id())
                .bind("personId", score.personId())
                .bind("modelId", score.modelId())
                .bind("score", score.score())
                .bind("calculatedAt", score.calculatedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(score)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CreditScoreEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM credit_scores WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CreditScoreEntity>> findLatestByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM credit_scores WHERE person_id = :personId ORDER BY calculated_at DESC LIMIT 1")
                .bind("personId", personId)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditScoreEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM credit_scores WHERE person_id = :personId ORDER BY calculated_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditScoreEntity>> findByModelId(UUID modelId) {
        return databaseClient.sql("SELECT * FROM credit_scores WHERE model_id = :modelId ORDER BY calculated_at DESC")
                .bind("modelId", modelId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM credit_scores WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private CreditScoreEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new CreditScoreEntity(
                row.get("id", UUID.class),
                row.get("person_id", UUID.class),
                row.get("model_id", UUID.class),
                row.get("score", Integer.class),
                row.get("calculated_at", LocalDateTime.class)
        );
    }
}
