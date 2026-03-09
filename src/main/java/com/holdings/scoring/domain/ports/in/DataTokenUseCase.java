package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.DataTokenEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la tokenización de identificadores sensibles del sistema.
 */
public interface DataTokenUseCase {

    CompletableFuture<DataTokenEntity> tokenize(String sensitiveData);

    CompletableFuture<Optional<DataTokenEntity>> findTokenById(UUID id);

    CompletableFuture<Optional<DataTokenEntity>> findByToken(String token);

    CompletableFuture<Optional<DataTokenEntity>> findByOriginalHash(String originalHash);

    CompletableFuture<Void> deleteToken(UUID id);

}
