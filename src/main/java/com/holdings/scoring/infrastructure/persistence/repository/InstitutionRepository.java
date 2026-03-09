package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.InstitutionEntity;
import com.holdings.scoring.domain.ports.out.InstitutionRepositoryPort;
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
public class InstitutionRepository implements InstitutionRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<InstitutionEntity> save(InstitutionEntity institution) {
        return databaseClient.sql("""
                        INSERT INTO institutions (id, name, tax_id, country, active, created_at, updated_at)
                        VALUES (:id, :name, :taxId, :country, :active, :createdAt, :updatedAt)
                        """)
                .bind("id", institution.id())
                .bind("name", institution.name())
                .bind("taxId", institution.taxId())
                .bind("country", institution.country())
                .bind("active", institution.active())
                .bind("createdAt", institution.createdAt())
                .bind("updatedAt", institution.updatedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(institution)
                .toFuture();
    }

    @Override
    public CompletableFuture<InstitutionEntity> update(InstitutionEntity institution) {
        return databaseClient.sql("""
                        UPDATE institutions
                        SET name       = :name,
                            tax_id     = :taxId,
                            country    = :country,
                            active     = :active,
                            updated_at = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", institution.id())
                .bind("name", institution.name())
                .bind("taxId", institution.taxId())
                .bind("country", institution.country())
                .bind("active", institution.active())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(institution)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM institutions WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<InstitutionEntity>> findByTaxId(String taxId) {
        return databaseClient.sql("SELECT * FROM institutions WHERE tax_id = :taxId")
                .bind("taxId", taxId)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionEntity>> findAll() {
        return databaseClient.sql("SELECT * FROM institutions ORDER BY created_at DESC")
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<InstitutionEntity>> findAllActive() {
        return databaseClient.sql("SELECT * FROM institutions WHERE active = true ORDER BY created_at DESC")
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM institutions WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private InstitutionEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new InstitutionEntity(
                row.get("id", UUID.class),
                row.get("name", String.class),
                row.get("tax_id", String.class),
                row.get("country", String.class),
                row.get("active", Boolean.class),
                row.get("created_at", LocalDateTime.class),
                row.get("updated_at", LocalDateTime.class)
        );
    }
}
