package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.PersonContactEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de información de contacto de una persona.
 */
public interface PersonContactUseCase {

    CompletableFuture<PersonContactEntity> createContact(PersonContactEntity contact);

    CompletableFuture<PersonContactEntity> updateContact(UUID id, PersonContactEntity contact);

    CompletableFuture<Optional<PersonContactEntity>> findContactById(UUID id);

    CompletableFuture<List<PersonContactEntity>> findContactsByPersonId(UUID personId);

    CompletableFuture<Void> deleteContact(UUID id);

}
