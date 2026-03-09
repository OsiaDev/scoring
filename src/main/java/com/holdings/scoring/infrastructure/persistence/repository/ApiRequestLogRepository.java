package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.ApiRequestLogEntity;
import com.holdings.scoring.domain.ports.out.ApiRequestLogRepositoryPort;
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
public class ApiRequestLogRepository implements ApiRequestLogRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<ApiRequestLogEntity> save(ApiRequestLogEntity log) {
        return databaseClient.sql("""
                        INSERT INTO api_request_logs
                            (id, institution_id, endpoint, method, ip_address, response_status, requested_at, created_at, updated_at)
                        VALUES
                            (:id, :institutionId, :endpoint, :method, :ipAddress, :responseStatus, :requestedAt, :requestedAt, :requestedAt)
                        """)
                .bind("id", log.id())
                .bind("institutionId", log.institutionId())
                .bind("endpoint", log.endpoint())
                .bind("method", log.method())
                .bind("ipAddress", log.ipAddress())
                .bind("responseStatus", log.responseStatus())
                .bind("requestedAt", log.requestedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(log)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ApiRequestLogEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM api_request_logs WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<ApiRequestLogEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM api_request_logs WHERE institution_id = :institutionId ORDER BY requested_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM api_request_logs WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private ApiRequestLogEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new ApiRequestLogEntity(
                row.get("id", UUID.class),
                row.get("institution_id", UUID.class),
                row.get("endpoint", String.class),
                row.get("method", String.class),
                row.get("ip_address", String.class),
                row.get("response_status", Integer.class),
                row.get("requested_at", LocalDateTime.class)
        );
    }
}
