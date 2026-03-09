package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.PersonContactEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PersonContactRepositoryPort {

    CompletableFuture<PersonContactEntity> save(PersonContactEntity contact);

    CompletableFuture<PersonContactEntity> update(PersonContactEntity contact);

    CompletableFuture<Optional<PersonContactEntity>> findById(UUID id);

    CompletableFuture<List<PersonContactEntity>> findByPersonId(UUID personId);

    CompletableFuture<Void> deleteById(UUID id);

}
