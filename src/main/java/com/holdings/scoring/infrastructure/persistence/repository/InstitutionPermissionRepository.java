package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.InstitutionPermissionEntity;
import com.holdings.scoring.domain.model.enums.InstitutionPermissionType;
import com.holdings.scoring.domain.ports.out.InstitutionPermissionRepositoryPort;
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
public class InstitutionPermissionRepository implements InstitutionPermissionRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<InstitutionPermissionEntity> save(InstitutionPermissionEntity permission) {
        return databaseClient.sql("""
                        INSERT INTO institution_permissions (id, institution_id, permission, created_at, updated_at)
                        VALUES (:id, :institutionId, :permission, :createdAt, :updatedAt)
                        """)
                .bind("id", permission.id())
                .bind("institutionId", permission.institutionId())
                .bind("permission", permission.permission().name())
                .bind("createdAt", permission.createdAt())
                .bind("updatedAt", permission.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(permission)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionPermissionEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM institution_permissions WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionPermissionEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM institution_permissions WHERE institution_id = :institutionId ORDER BY created_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Boolean> existsByInstitutionIdAndPermission(UUID institutionId, InstitutionPermissionType permission) {
        return databaseClient.sql("""
                        SELECT COUNT(*) FROM institution_permissions
                        WHERE institution_id = :institutionId AND permission = :permission
                        """)
                .bind("institutionId", institutionId)
                .bind("permission", permission.name())
                .map((row, metadata) -> row.get(0, Long.class))
                .one()
                .map(count -> count != null && count > 0)
                .defaultIfEmpty(false)
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM institution_permissions WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteByInstitutionId(UUID institutionId) {
        return databaseClient.sql("DELETE FROM institution_permissions WHERE institution_id = :institutionId")
                .bind("institutionId", institutionId)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private InstitutionPermissionEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new InstitutionPermissionEntity(
                row.get("id", UUID.class),
                row.get("institution_id", UUID.class),
                InstitutionPermissionType.valueOf(row.get("permission", String.class)),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
