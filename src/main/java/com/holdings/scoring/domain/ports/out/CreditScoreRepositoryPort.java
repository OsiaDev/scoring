package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.CreditScoreEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreditScoreRepositoryPort {

    CompletableFuture<CreditScoreEntity> save(CreditScoreEntity score);

    CompletableFuture<Optional<CreditScoreEntity>> findById(UUID id);

    CompletableFuture<Optional<CreditScoreEntity>> findLatestByPersonId(UUID personId);

    CompletableFuture<List<CreditScoreEntity>> findByPersonId(UUID personId);

    CompletableFuture<List<CreditScoreEntity>> findByModelId(UUID modelId);

    CompletableFuture<Void> deleteById(UUID id);

}
