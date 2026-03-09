package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.DataAccessLogEntity;
import com.holdings.scoring.domain.ports.out.DataAccessLogRepositoryPort;
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
public class DataAccessLogRepository implements DataAccessLogRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<DataAccessLogEntity> save(DataAccessLogEntity log) {
        return databaseClient.sql("""
                        INSERT INTO data_access_logs (id, institution_id, person_id, action, accessed_at, created_at, updated_at)
                        VALUES (:id, :institutionId, :personId, :action, :accessedAt, :accessedAt, :accessedAt)
                        """)
                .bind("id", log.id())
                .bind("institutionId", log.institutionId())
                .bind("personId", log.personId())
                .bind("action", log.action())
                .bind("accessedAt", log.accessedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(log)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<DataAccessLogEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM data_access_logs WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<DataAccessLogEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM data_access_logs WHERE institution_id = :institutionId ORDER BY accessed_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<DataAccessLogEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM data_access_logs WHERE person_id = :personId ORDER BY accessed_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM data_access_logs WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private DataAccessLogEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new DataAccessLogEntity(
                row.get("id", UUID.class),
                row.get("institution_id", UUID.class),
                row.get("person_id", UUID.class),
                row.get("action", String.class),
                row.get("accessed_at", LocalDateTime.class)
        );
    }
}
