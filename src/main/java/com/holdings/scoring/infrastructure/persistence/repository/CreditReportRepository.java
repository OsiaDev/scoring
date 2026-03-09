package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.CreditReportEntity;
import com.holdings.scoring.domain.ports.out.CreditReportRepositoryPort;
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
public class CreditReportRepository implements CreditReportRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<CreditReportEntity> save(CreditReportEntity report) {
        return databaseClient.sql("""
                        INSERT INTO credit_reports (id, person_id, institution_id, reported_at, created_at, updated_at)
                        VALUES (:id, :personId, :institutionId, :reportedAt, :reportedAt, :reportedAt)
                        """)
                .bind("id", report.id())
                .bind("personId", report.personId())
                .bind("institutionId", report.institutionId())
                .bind("reportedAt", report.reportedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(report)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CreditReportEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM credit_reports WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditReportEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM credit_reports WHERE person_id = :personId ORDER BY reported_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditReportEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM credit_reports WHERE institution_id = :institutionId ORDER BY reported_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM credit_reports WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private CreditReportEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new CreditReportEntity(
                row.get("id", UUID.class),
                row.get("person_id", UUID.class),
                row.get("institution_id", UUID.class),
                row.get("reported_at", LocalDateTime.class)
        );
    }
}
