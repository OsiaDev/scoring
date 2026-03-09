package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.InstitutionUserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface InstitutionUserRepositoryPort {

    CompletableFuture<InstitutionUserEntity> save(InstitutionUserEntity user);

    CompletableFuture<InstitutionUserEntity> update(InstitutionUserEntity user);

    CompletableFuture<Optional<InstitutionUserEntity>> findById(UUID id);

    CompletableFuture<Optional<InstitutionUserEntity>> findByEmail(String email);

    CompletableFuture<List<InstitutionUserEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<List<InstitutionUserEntity>> findActiveByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteById(UUID id);

}
