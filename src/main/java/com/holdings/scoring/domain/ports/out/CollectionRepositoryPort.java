package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.CollectionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CollectionRepositoryPort {

    CompletableFuture<CollectionEntity> save(CollectionEntity collection);

    CompletableFuture<Optional<CollectionEntity>> findById(UUID id);

    CompletableFuture<List<CollectionEntity>> findByCreditAccountId(UUID creditAccountId);

    CompletableFuture<Void> deleteById(UUID id);

}
