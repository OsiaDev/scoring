package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.ScoreModelEntity;
import com.holdings.scoring.domain.ports.out.ScoreModelRepositoryPort;
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
public class ScoreModelRepository implements ScoreModelRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<ScoreModelEntity> save(ScoreModelEntity model) {
        return databaseClient.sql("""
                        INSERT INTO score_models (id, name, version, active, created_at, updated_at)
                        VALUES (:id, :name, :version, :active, :createdAt, :createdAt)
                        """)
                .bind("id", model.id())
                .bind("name", model.name())
                .bind("version", model.version())
                .bind("active", model.active())
                .bind("createdAt", model.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(model)
                .toFuture();
    }

    @Override
    public CompletableFuture<ScoreModelEntity> update(ScoreModelEntity model) {
        return databaseClient.sql("""
                        UPDATE score_models
                        SET name       = :name,
                            version    = :version,
                            active     = :active,
                            updated_at = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", model.id())
                .bind("name", model.name())
                .bind("version", model.version())
                .bind("active", model.active())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(model)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ScoreModelEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM score_models WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ScoreModelEntity>> findActiveModel() {
        return databaseClient.sql("SELECT * FROM score_models WHERE active = true LIMIT 1")
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ScoreModelEntity>> findAll() {
        return databaseClient.sql("SELECT * FROM score_models ORDER BY created_at DESC")
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM score_models WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private ScoreModelEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new ScoreModelEntity(
                row.get("id", UUID.class),
                row.get("name", String.class),
                row.get("version", String.class),
                row.get("active", Boolean.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
