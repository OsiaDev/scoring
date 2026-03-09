package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.ConsentHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ConsentHistoryRepositoryPort {

    CompletableFuture<ConsentHistoryEntity> save(ConsentHistoryEntity history);

    CompletableFuture<Optional<ConsentHistoryEntity>> findById(UUID id);

    CompletableFuture<List<ConsentHistoryEntity>> findByConsentId(UUID consentId);

    CompletableFuture<Void> deleteById(UUID id);

}
