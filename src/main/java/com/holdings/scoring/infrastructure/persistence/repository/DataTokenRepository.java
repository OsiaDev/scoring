package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.DataTokenEntity;
import com.holdings.scoring.domain.ports.out.DataTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class DataTokenRepository implements DataTokenRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<DataTokenEntity> save(DataTokenEntity dataToken) {
        return databaseClient.sql("""
                        INSERT INTO data_tokens (id, token, original_hash, created_at, updated_at)
                        VALUES (:id, :token, :originalHash, :createdAt, :createdAt)
                        """)
                .bind("id", dataToken.id())
                .bind("token", dataToken.token())
                .bind("originalHash", dataToken.originalHash())
                .bind("createdAt", dataToken.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(dataToken)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<DataTokenEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM data_tokens WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<DataTokenEntity>> findByToken(String token) {
        return databaseClient.sql("SELECT * FROM data_tokens WHERE token = :token")
                .bind("token", token)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<DataTokenEntity>> findByOriginalHash(String originalHash) {
        return databaseClient.sql("SELECT * FROM data_tokens WHERE original_hash = :originalHash")
                .bind("originalHash", originalHash)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM data_tokens WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private DataTokenEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new DataTokenEntity(
                row.get("id", UUID.class),
                row.get("token", String.class),
                row.get("original_hash", String.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
