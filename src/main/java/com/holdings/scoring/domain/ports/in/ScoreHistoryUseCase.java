package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.ScoreHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la consulta del historial de scores calculados para una persona.
 */
public interface ScoreHistoryUseCase {

    CompletableFuture<ScoreHistoryEntity> registerScoreHistory(ScoreHistoryEntity history);

    CompletableFuture<Optional<ScoreHistoryEntity>> findScoreHistoryById(UUID id);

    CompletableFuture<List<ScoreHistoryEntity>> findScoreHistoryByPersonId(UUID personId);

    CompletableFuture<Void> deleteScoreHistory(UUID id);

}
