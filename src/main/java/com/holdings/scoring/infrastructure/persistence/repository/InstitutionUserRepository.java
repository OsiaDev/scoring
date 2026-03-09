package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.InstitutionUserEntity;
import com.holdings.scoring.domain.ports.out.InstitutionUserRepositoryPort;
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
public class InstitutionUserRepository implements InstitutionUserRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<InstitutionUserEntity> save(InstitutionUserEntity user) {
        return databaseClient.sql("""
                        INSERT INTO institution_users (id, institution_id, email, password_hash, role, active, created_at, updated_at)
                        VALUES (:id, :institutionId, :email, :passwordHash, :role, :active, :createdAt, :updatedAt)
                        """)
                .bind("id", user.id())
                .bind("institutionId", user.institutionId())
                .bind("email", user.email())
                .bind("passwordHash", user.passwordHash())
                .bind("role", user.role())
                .bind("active", user.active())
                .bind("createdAt", user.createdAt())
                .bind("updatedAt", user.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(user)
                .toFuture();
    }

    @Override
    public CompletableFuture<InstitutionUserEntity> update(InstitutionUserEntity user) {
        return databaseClient.sql("""
                        UPDATE institution_users
                        SET email         = :email,
                            password_hash = :passwordHash,
                            role          = :role,
                            active        = :active,
                            updated_at    = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", user.id())
                .bind("email", user.email())
                .bind("passwordHash", user.passwordHash())
                .bind("role", user.role())
                .bind("active", user.active())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(user)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionUserEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM institution_users WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionUserEntity>> findByEmail(String email) {
        return databaseClient.sql("SELECT * FROM institution_users WHERE email = :email")
                .bind("email", email)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionUserEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM institution_users WHERE institution_id = :institutionId ORDER BY created_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionUserEntity>> findActiveByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM institution_users WHERE institution_id = :institutionId AND active = true ORDER BY created_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM institution_users WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private InstitutionUserEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new InstitutionUserEntity(
                row.get("id", UUID.class),
                row.get("institution_id", UUID.class),
                row.get("email", String.class),
                row.get("password_hash", String.class),
                row.get("role", String.class),
                row.get("active", Boolean.class),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
