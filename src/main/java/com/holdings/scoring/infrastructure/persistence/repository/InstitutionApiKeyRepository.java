package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.InstitutionApiKeyEntity;
import com.holdings.scoring.domain.ports.out.InstitutionApiKeyRepositoryPort;
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
public class InstitutionApiKeyRepository implements InstitutionApiKeyRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<InstitutionApiKeyEntity> save(InstitutionApiKeyEntity apiKey) {
        return databaseClient.sql("""
                        INSERT INTO institution_api_keys (id, institution_id, api_key_hash, expires_at, active, created_at, updated_at)
                        VALUES (:id, :institutionId, :apiKeyHash, :expiresAt, :active, :createdAt, :updatedAt)
                        """)
                .bind("id", apiKey.id())
                .bind("institutionId", apiKey.institutionId())
                .bind("apiKeyHash", apiKey.apiKeyHash())
                .bind("expiresAt", apiKey.expiresAt())
                .bind("active", apiKey.active())
                .bind("createdAt", apiKey.createdAt())
                .bind("updatedAt", apiKey.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(apiKey)
                .toFuture();
    }

    @Override
    public CompletableFuture<InstitutionApiKeyEntity> update(InstitutionApiKeyEntity apiKey) {
        return databaseClient.sql("""
                        UPDATE institution_api_keys
                        SET expires_at = :expiresAt,
                            active     = :active,
                            updated_at = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", apiKey.id())
                .bind("expiresAt", apiKey.expiresAt())
                .bind("active", apiKey.active())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(apiKey)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionApiKeyEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM institution_api_keys WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionApiKeyEntity>> findByApiKeyHash(String apiKeyHash) {
        return databaseClient.sql("SELECT * FROM institution_api_keys WHERE api_key_hash = :apiKeyHash AND active = true")
                .bind("apiKeyHash", apiKeyHash)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionApiKeyEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM institution_api_keys WHERE institution_id = :institutionId ORDER BY created_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionApiKeyEntity>> findActiveByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM institution_api_keys WHERE institution_id = :institutionId AND active = true ORDER BY created_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM institution_api_keys WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private InstitutionApiKeyEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new InstitutionApiKeyEntity(
                row.get("id", UUID.class),
                row.get("institution_id", UUID.class),
                row.get("api_key_hash", String.class),
                row.get("expires_at", LocalDateTime.class),
                row.get("active", Boolean.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
