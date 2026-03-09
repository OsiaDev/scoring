package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.PersonEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PersonRepositoryPort {

    CompletableFuture<PersonEntity> save(PersonEntity person);

    CompletableFuture<PersonEntity> update(PersonEntity person);

    CompletableFuture<Optional<PersonEntity>> findById(UUID id);

    CompletableFuture<List<PersonEntity>> findAll();

    CompletableFuture<Void> deleteById(UUID id);

}
