package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.PaymentEntity;
import com.holdings.scoring.domain.model.enums.PaymentMethod;
import com.holdings.scoring.domain.ports.out.PaymentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class PaymentRepository implements PaymentRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<PaymentEntity> save(PaymentEntity payment) {
        return databaseClient.sql("""
                        INSERT INTO payments (id, credit_account_id, amount, payment_date, payment_method, created_at, updated_at)
                        VALUES (:id, :creditAccountId, :amount, :paymentDate, :paymentMethod, :createdAt, :createdAt)
                        """)
                .bind("id", payment.id())
                .bind("creditAccountId", payment.creditAccountId())
                .bind("amount", payment.amount())
                .bind("paymentDate", payment.paymentDate())
                .bind("paymentMethod", payment.paymentMethod().name())
                .bind("createdAt", payment.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(payment)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<PaymentEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM payments WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<PaymentEntity>> findByCreditAccountId(UUID creditAccountId) {
        return databaseClient.sql("SELECT * FROM payments WHERE credit_account_id = :creditAccountId ORDER BY payment_date DESC")
                .bind("creditAccountId", creditAccountId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM payments WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private PaymentEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new PaymentEntity(
                row.get("id", UUID.class),
                row.get("credit_account_id", UUID.class),
                row.get("amount", BigDecimal.class),
                row.get("payment_date", LocalDate.class),
                PaymentMethod.valueOf(row.get("payment_method", String.class)),
                row.get("created_at", LocalDateTime.class)
        );
    }
}
