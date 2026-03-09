package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.ConsentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión del consentimiento de titulares de datos (Habeas Data).
 */
public interface ConsentUseCase {

    CompletableFuture<ConsentEntity> grantConsent(ConsentEntity consent);

    CompletableFuture<ConsentEntity> revokeConsent(UUID id);

    CompletableFuture<Optional<ConsentEntity>> findConsentById(UUID id);

    CompletableFuture<Optional<ConsentEntity>> findConsentByPersonAndInstitution(UUID personId, UUID institutionId);

    CompletableFuture<List<ConsentEntity>> findConsentsByPersonId(UUID personId);

    CompletableFuture<List<ConsentEntity>> findGrantedConsentsByPersonId(UUID personId);

    CompletableFuture<Void> deleteConsent(UUID id);

}
