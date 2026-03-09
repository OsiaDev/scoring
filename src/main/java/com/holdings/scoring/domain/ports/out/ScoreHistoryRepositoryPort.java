package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.ScoreHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ScoreHistoryRepositoryPort {

    CompletableFuture<ScoreHistoryEntity> save(ScoreHistoryEntity history);

    CompletableFuture<Optional<ScoreHistoryEntity>> findById(UUID id);

    CompletableFuture<List<ScoreHistoryEntity>> findByPersonId(UUID personId);

    CompletableFuture<Void> deleteById(UUID id);

}
