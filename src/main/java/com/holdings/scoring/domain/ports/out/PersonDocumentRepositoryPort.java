package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.PersonDocumentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PersonDocumentRepositoryPort {

    CompletableFuture<PersonDocumentEntity> save(PersonDocumentEntity document);

    CompletableFuture<PersonDocumentEntity> update(PersonDocumentEntity document);

    CompletableFuture<Optional<PersonDocumentEntity>> findById(UUID id);

    CompletableFuture<List<PersonDocumentEntity>> findByPersonId(UUID personId);

    CompletableFuture<Void> deleteById(UUID id);

}
