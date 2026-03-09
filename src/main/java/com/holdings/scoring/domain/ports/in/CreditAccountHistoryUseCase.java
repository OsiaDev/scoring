package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.CreditAccountHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el registro y consulta del historial de estado de una cuenta de crédito.
 */
public interface CreditAccountHistoryUseCase {

    CompletableFuture<CreditAccountHistoryEntity> registerHistory(CreditAccountHistoryEntity history);

    CompletableFuture<Optional<CreditAccountHistoryEntity>> findHistoryById(UUID id);

    CompletableFuture<List<CreditAccountHistoryEntity>> findHistoryByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteHistory(UUID id);

}
