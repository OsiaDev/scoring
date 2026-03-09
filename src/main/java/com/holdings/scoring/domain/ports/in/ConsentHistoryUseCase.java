package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.ConsentHistoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la auditoría del historial de cambios de consentimiento.
 */
public interface ConsentHistoryUseCase {

    CompletableFuture<ConsentHistoryEntity> registerConsentChange(ConsentHistoryEntity history);

    CompletableFuture<Optional<ConsentHistoryEntity>> findConsentHistoryById(UUID id);

    CompletableFuture<List<ConsentHistoryEntity>> findConsentHistoryByConsentId(UUID consentId);

    CompletableFuture<Void> deleteConsentHistory(UUID id);

}
