package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.ScoreFactorEntity;
import com.holdings.scoring.domain.ports.out.ScoreFactorRepositoryPort;
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
public class ScoreFactorRepository implements ScoreFactorRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<ScoreFactorEntity> save(ScoreFactorEntity factor) {
        return databaseClient.sql("""
                        INSERT INTO score_factors (id, credit_score_id, factor, weight, description, created_at, updated_at)
                        VALUES (:id, :creditScoreId, :factor, :weight, :description, :now, :now)
                        """)
                .bind("id", factor.id())
                .bind("creditScoreId", factor.creditScoreId())
                .bind("factor", factor.factor())
                .bind("weight", factor.weight())
                .bind("description", factor.description())
                .bind("now", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(factor)
                .toFuture();
    }

    @Override
    public CompletableFuture<ScoreFactorEntity> update(ScoreFactorEntity factor) {
        return databaseClient.sql("""
                        UPDATE score_factors
                        SET factor      = :factor,
                            weight      = :weight,
                            description = :description,
                            updated_at  = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", factor.id())
                .bind("factor", factor.factor())
                .bind("weight", factor.weight())
                .bind("description", factor.description())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(factor)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ScoreFactorEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM score_factors WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ScoreFactorEntity>> findByCreditScoreId(UUID creditScoreId) {
        return databaseClient.sql("SELECT * FROM score_factors WHERE credit_score_id = :creditScoreId")
                .bind("creditScoreId", creditScoreId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM score_factors WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteByCreditScoreId(UUID creditScoreId) {
        return databaseClient.sql("DELETE FROM score_factors WHERE credit_score_id = :creditScoreId")
                .bind("creditScoreId", creditScoreId)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private ScoreFactorEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new ScoreFactorEntity(
                row.get("id", UUID.class),
                row.get("credit_score_id", UUID.class),
                row.get("factor", String.class),
                row.get("weight", BigDecimal.class),
                row.get("description", String.class)
        );
    }
}
