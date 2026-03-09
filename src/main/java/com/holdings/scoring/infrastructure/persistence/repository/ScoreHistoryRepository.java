package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.ScoreHistoryEntity;
import com.holdings.scoring.domain.ports.out.ScoreHistoryRepositoryPort;
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
public class ScoreHistoryRepository implements ScoreHistoryRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<ScoreHistoryEntity> save(ScoreHistoryEntity history) {
        return databaseClient.sql("""
                        INSERT INTO score_history (id, person_id, score, calculated_at, created_at, updated_at)
                        VALUES (:id, :personId, :score, :calculatedAt, :calculatedAt, :calculatedAt)
                        """)
                .bind("id", history.id())
                .bind("personId", history.personId())
                .bind("score", history.score())
                .bind("calculatedAt", history.calculatedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(history)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ScoreHistoryEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM score_history WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ScoreHistoryEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM score_history WHERE person_id = :personId ORDER BY calculated_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM score_history WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private ScoreHistoryEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new ScoreHistoryEntity(
                row.get("id", UUID.class),
                row.get("person_id", UUID.class),
                row.get("score", Integer.class),
                row.get("calculated_at", LocalDateTime.class)
        );
    }
}
