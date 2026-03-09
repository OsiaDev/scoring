package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.ScoreModelEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de modelos de cálculo de score crediticio.
 */
public interface ScoreModelUseCase {

    CompletableFuture<ScoreModelEntity> createModel(ScoreModelEntity model);

    CompletableFuture<ScoreModelEntity> updateModel(UUID id, ScoreModelEntity model);

    CompletableFuture<Optional<ScoreModelEntity>> findModelById(UUID id);

    CompletableFuture<Optional<ScoreModelEntity>> findActiveModel();

    CompletableFuture<List<ScoreModelEntity>> findAllModels();

    CompletableFuture<ScoreModelEntity> activateModel(UUID id);

    CompletableFuture<Void> deleteModel(UUID id);

}
