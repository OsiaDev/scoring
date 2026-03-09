package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.PersonAddressEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PersonAddressRepositoryPort {

    CompletableFuture<PersonAddressEntity> save(PersonAddressEntity address);

    CompletableFuture<PersonAddressEntity> update(PersonAddressEntity address);

    CompletableFuture<Optional<PersonAddressEntity>> findById(UUID id);

    CompletableFuture<List<PersonAddressEntity>> findByPersonId(UUID personId);

    CompletableFuture<Void> deleteById(UUID id);

}
