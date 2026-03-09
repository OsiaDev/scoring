package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.CreditReportDetailEntity;
import com.holdings.scoring.domain.ports.out.CreditReportDetailRepositoryPort;
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
public class CreditReportDetailRepository implements CreditReportDetailRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<CreditReportDetailEntity> save(CreditReportDetailEntity detail) {
        return databaseClient.sql("""
                        INSERT INTO credit_report_details (id, credit_report_id, field_name, field_value, created_at, updated_at)
                        VALUES (:id, :creditReportId, :fieldName, :fieldValue, :now, :now)
                        """)
                .bind("id", detail.id())
                .bind("creditReportId", detail.creditReportId())
                .bind("fieldName", detail.fieldName())
                .bind("fieldValue", detail.fieldValue())
                .bind("now", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(detail)
                .toFuture();
    }

    @Override
    public CompletableFuture<CreditReportDetailEntity> update(CreditReportDetailEntity detail) {
        return databaseClient.sql("""
                        UPDATE credit_report_details
                        SET field_value = :fieldValue,
                            updated_at  = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", detail.id())
                .bind("fieldValue", detail.fieldValue())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(detail)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CreditReportDetailEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM credit_report_details WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditReportDetailEntity>> findByCreditReportId(UUID creditReportId) {
        return databaseClient.sql("SELECT * FROM credit_report_details WHERE credit_report_id = :creditReportId")
                .bind("creditReportId", creditReportId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM credit_report_details WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteByCreditReportId(UUID creditReportId) {
        return databaseClient.sql("DELETE FROM credit_report_details WHERE credit_report_id = :creditReportId")
                .bind("creditReportId", creditReportId)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private CreditReportDetailEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new CreditReportDetailEntity(
                row.get("id", UUID.class),
                row.get("credit_report_id", UUID.class),
                row.get("field_name", String.class),
                row.get("field_value", String.class)
        );
    }
}
