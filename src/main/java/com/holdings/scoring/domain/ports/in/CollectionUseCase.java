package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.CollectionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de deudas transferidas a procesos de cobranza.
 */
public interface CollectionUseCase {

    CompletableFuture<CollectionEntity> registerCollection(CollectionEntity collection);

    CompletableFuture<Optional<CollectionEntity>> findCollectionById(UUID id);

    CompletableFuture<List<CollectionEntity>> findCollectionsByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteCollection(UUID id);

}
