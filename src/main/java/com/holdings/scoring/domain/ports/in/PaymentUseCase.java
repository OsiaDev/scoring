package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el registro y consulta de pagos realizados sobre obligaciones financieras.
 */
public interface PaymentUseCase {

    CompletableFuture<PaymentEntity> registerPayment(PaymentEntity payment);

    CompletableFuture<Optional<PaymentEntity>> findPaymentById(UUID id);

    CompletableFuture<List<PaymentEntity>> findPaymentsByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deletePayment(UUID id);

}
