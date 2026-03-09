package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.ConsentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ConsentRepositoryPort {

    CompletableFuture<ConsentEntity> save(ConsentEntity consent);

    CompletableFuture<ConsentEntity> update(ConsentEntity consent);

    CompletableFuture<Optional<ConsentEntity>> findById(UUID id);

    CompletableFuture<Optional<ConsentEntity>> findByPersonIdAndInstitutionId(UUID personId, UUID institutionId);

    CompletableFuture<List<ConsentEntity>> findByPersonId(UUID personId);

    CompletableFuture<List<ConsentEntity>> findGrantedByPersonId(UUID personId);

    CompletableFuture<Void> deleteById(UUID id);

}
