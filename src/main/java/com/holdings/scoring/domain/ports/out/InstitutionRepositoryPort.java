package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.InstitutionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface InstitutionRepositoryPort {

    CompletableFuture<InstitutionEntity> save(InstitutionEntity institution);

    CompletableFuture<InstitutionEntity> update(InstitutionEntity institution);

    CompletableFuture<Optional<InstitutionEntity>> findById(UUID id);

    CompletableFuture<Optional<InstitutionEntity>> findByTaxId(String taxId);

    CompletableFuture<List<InstitutionEntity>> findAll();

    CompletableFuture<List<InstitutionEntity>> findAllActive();

    CompletableFuture<Void> deleteById(UUID id);

}
