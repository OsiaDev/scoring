package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.PersonDocumentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de documentos de identidad de una persona.
 */
public interface PersonDocumentUseCase {

    CompletableFuture<PersonDocumentEntity> createDocument(PersonDocumentEntity document);

    CompletableFuture<PersonDocumentEntity> updateDocument(UUID id, PersonDocumentEntity document);

    CompletableFuture<Optional<PersonDocumentEntity>> findDocumentById(UUID id);

    CompletableFuture<List<PersonDocumentEntity>> findDocumentsByPersonId(UUID personId);

    CompletableFuture<Void> deleteDocument(UUID id);

}
