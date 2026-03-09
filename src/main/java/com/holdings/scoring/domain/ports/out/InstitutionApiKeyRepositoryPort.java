package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.InstitutionApiKeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface InstitutionApiKeyRepositoryPort {

    CompletableFuture<InstitutionApiKeyEntity> save(InstitutionApiKeyEntity apiKey);

    CompletableFuture<InstitutionApiKeyEntity> update(InstitutionApiKeyEntity apiKey);

    CompletableFuture<Optional<InstitutionApiKeyEntity>> findById(UUID id);

    CompletableFuture<Optional<InstitutionApiKeyEntity>> findByApiKeyHash(String apiKeyHash);

    CompletableFuture<List<InstitutionApiKeyEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<List<InstitutionApiKeyEntity>> findActiveByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteById(UUID id);

}
