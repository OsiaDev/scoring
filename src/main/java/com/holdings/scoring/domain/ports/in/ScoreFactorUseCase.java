package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.ScoreFactorEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de factores que componen el cálculo de un score crediticio.
 */
public interface ScoreFactorUseCase {

    CompletableFuture<ScoreFactorEntity> createFactor(ScoreFactorEntity factor);

    CompletableFuture<ScoreFactorEntity> updateFactor(UUID id, ScoreFactorEntity factor);

    CompletableFuture<Optional<ScoreFactorEntity>> findFactorById(UUID id);

    CompletableFuture<List<ScoreFactorEntity>> findFactorsByCreditScoreId(UUID creditScoreId);

    CompletableFuture<Void> deleteFactor(UUID id);

    CompletableFuture<Void> deleteFactorsByCreditScoreId(UUID creditScoreId);

}
