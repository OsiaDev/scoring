package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PaymentRepositoryPort {

    CompletableFuture<PaymentEntity> save(PaymentEntity payment);

    CompletableFuture<Optional<PaymentEntity>> findById(UUID id);

    CompletableFuture<List<PaymentEntity>> findByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteById(UUID id);

}
