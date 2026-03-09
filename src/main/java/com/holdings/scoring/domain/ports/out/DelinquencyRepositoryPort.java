package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.DelinquencyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DelinquencyRepositoryPort {

    CompletableFuture<DelinquencyEntity> save(DelinquencyEntity delinquency);

    CompletableFuture<Optional<DelinquencyEntity>> findById(UUID id);

    CompletableFuture<List<DelinquencyEntity>> findByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteById(UUID id);

}
