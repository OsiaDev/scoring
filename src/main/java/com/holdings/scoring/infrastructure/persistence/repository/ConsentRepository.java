package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.ConsentEntity;
import com.holdings.scoring.domain.ports.out.ConsentRepositoryPort;
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
public class ConsentRepository implements ConsentRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<ConsentEntity> save(ConsentEntity consent) {
        return databaseClient.sql("""
                        INSERT INTO consents (id, person_id, institution_id, granted, source, granted_at, revoked_at, created_at, updated_at)
                        VALUES (:id, :personId, :institutionId, :granted, :source, :grantedAt, :revokedAt, :grantedAt, :grantedAt)
                        """)
                .bind("id", consent.id())
                .bind("personId", consent.personId())
                .bind("institutionId", consent.institutionId())
                .bind("granted", consent.granted())
                .bind("source", consent.source())
                .bind("grantedAt", consent.grantedAt())
                .bindNull("revokedAt", LocalDateTime.class)
                .fetch()
                .rowsUpdated()
                .thenReturn(consent)
                .toFuture();
    }

    @Override
    public CompletableFuture<ConsentEntity> update(ConsentEntity consent) {
        return databaseClient.sql("""
                        UPDATE consents
                        SET granted    = :granted,
                            revoked_at = :revokedAt,
                            updated_at = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", consent.id())
                .bind("granted", consent.granted())
                .bind("revokedAt", consent.revokedAt())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(consent)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ConsentEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM consents WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ConsentEntity>> findByPersonIdAndInstitutionId(UUID personId, UUID institutionId) {
        return databaseClient.sql("SELECT * FROM consents WHERE person_id = :personId AND institution_id = :institutionId")
                .bind("personId", personId)
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ConsentEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM consents WHERE person_id = :personId ORDER BY granted_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ConsentEntity>> findGrantedByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM consents WHERE person_id = :personId AND granted = true ORDER BY granted_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM consents WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private ConsentEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new ConsentEntity(
                row.get("id", UUID.class),
                row.get("person_id", UUID.class),
                row.get("institution_id", UUID.class),
                row.get("granted", Boolean.class),
                row.get("source", String.class),
                row.get("granted_at", LocalDateTime.class),
                row.get("revoked_at", LocalDateTime.class)
        );
    }
}
