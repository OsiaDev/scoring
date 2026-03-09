package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.DataTokenEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DataTokenRepositoryPort {

    CompletableFuture<DataTokenEntity> save(DataTokenEntity dataToken);

    CompletableFuture<Optional<DataTokenEntity>> findById(UUID id);

    CompletableFuture<Optional<DataTokenEntity>> findByToken(String token);

    CompletableFuture<Optional<DataTokenEntity>> findByOriginalHash(String originalHash);

    CompletableFuture<Void> deleteById(UUID id);

}
