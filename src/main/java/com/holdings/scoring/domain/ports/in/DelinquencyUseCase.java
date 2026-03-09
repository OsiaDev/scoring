package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.DelinquencyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el registro y consulta de eventos de mora en obligaciones financieras.
 */
public interface DelinquencyUseCase {

    CompletableFuture<DelinquencyEntity> registerDelinquency(DelinquencyEntity delinquency);

    CompletableFuture<Optional<DelinquencyEntity>> findDelinquencyById(UUID id);

    CompletableFuture<List<DelinquencyEntity>> findDelinquenciesByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteDelinquency(UUID id);

}
