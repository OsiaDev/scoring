package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.CreditAccountHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreditAccountHistoryRepositoryPort {

    CompletableFuture<CreditAccountHistoryEntity> save(CreditAccountHistoryEntity history);

    CompletableFuture<Optional<CreditAccountHistoryEntity>> findById(UUID id);

    CompletableFuture<List<CreditAccountHistoryEntity>> findByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteById(UUID id);

}
