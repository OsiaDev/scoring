package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.EncryptionKeyEntity;
import com.holdings.scoring.domain.ports.out.EncryptionKeyRepositoryPort;
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
public class EncryptionKeyRepository implements EncryptionKeyRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<EncryptionKeyEntity> save(EncryptionKeyEntity encryptionKey) {
        return databaseClient.sql("""
                        INSERT INTO encryption_keys (id, key_alias, algorithm, active, created_at, updated_at)
                        VALUES (:id, :keyAlias, :algorithm, :active, :createdAt, :createdAt)
                        """)
                .bind("id", encryptionKey.id())
                .bind("keyAlias", encryptionKey.keyAlias())
                .bind("algorithm", encryptionKey.algorithm())
                .bind("active", encryptionKey.active())
                .bind("createdAt", encryptionKey.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(encryptionKey)
                .toFuture();
    }

    @Override
    public CompletableFuture<EncryptionKeyEntity> update(EncryptionKeyEntity encryptionKey) {
        return databaseClient.sql("""
                        UPDATE encryption_keys
                        SET key_alias  = :keyAlias,
                            algorithm  = :algorithm,
                            active     = :active,
                            updated_at = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", encryptionKey.id())
                .bind("keyAlias", encryptionKey.keyAlias())
                .bind("algorithm", encryptionKey.algorithm())
                .bind("active", encryptionKey.active())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(encryptionKey)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<EncryptionKeyEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM encryption_keys WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<EncryptionKeyEntity>> findByKeyAlias(String keyAlias) {
        return databaseClient.sql("SELECT * FROM encryption_keys WHERE key_alias = :keyAlias")
                .bind("keyAlias", keyAlias)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<EncryptionKeyEntity>> findActiveByAlgorithm(String algorithm) {
        return databaseClient.sql("SELECT * FROM encryption_keys WHERE algorithm = :algorithm AND active = true LIMIT 1")
                .bind("algorithm", algorithm)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<EncryptionKeyEntity>> findAll() {
        return databaseClient.sql("SELECT * FROM encryption_keys ORDER BY created_at DESC")
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM encryption_keys WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private EncryptionKeyEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new EncryptionKeyEntity(
                row.get("id", UUID.class),
                row.get("key_alias", String.class),
                row.get("algorithm", String.class),
                row.get("active", Boolean.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
