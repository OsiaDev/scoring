package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.ScoreModelEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ScoreModelRepositoryPort {

    CompletableFuture<ScoreModelEntity> save(ScoreModelEntity model);

    CompletableFuture<ScoreModelEntity> update(ScoreModelEntity model);

    CompletableFuture<Optional<ScoreModelEntity>> findById(UUID id);

    CompletableFuture<Optional<ScoreModelEntity>> findActiveModel();

    CompletableFuture<List<ScoreModelEntity>> findAll();

    CompletableFuture<Void> deleteById(UUID id);

}
