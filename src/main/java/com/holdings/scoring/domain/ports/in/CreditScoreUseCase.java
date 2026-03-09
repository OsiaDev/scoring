package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.CreditScoreEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el cálculo y consulta del score crediticio de una persona.
 */
public interface CreditScoreUseCase {

    CompletableFuture<CreditScoreEntity> calculateScore(UUID personId);

    CompletableFuture<Optional<CreditScoreEntity>> findScoreById(UUID id);

    CompletableFuture<Optional<CreditScoreEntity>> findLatestScoreByPersonId(UUID personId);

    CompletableFuture<List<CreditScoreEntity>> findScoresByPersonId(UUID personId);

    CompletableFuture<List<CreditScoreEntity>> findScoresByModelId(UUID modelId);

    CompletableFuture<Void> deleteScore(UUID id);

}
