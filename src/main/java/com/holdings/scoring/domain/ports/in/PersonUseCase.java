package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.PersonEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de personas registradas en el sistema de scoring.
 */
public interface PersonUseCase {

    CompletableFuture<PersonEntity> createPerson(PersonEntity person);

    CompletableFuture<PersonEntity> updatePerson(UUID id, PersonEntity person);

    CompletableFuture<Optional<PersonEntity>> findPersonById(UUID id);

    CompletableFuture<List<PersonEntity>> findAllPersons();

    CompletableFuture<Void> deletePerson(UUID id);

}
