package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.InstitutionApiKeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de credenciales de acceso (API Keys) de instituciones.
 */
public interface InstitutionApiKeyUseCase {

    CompletableFuture<InstitutionApiKeyEntity> createApiKey(InstitutionApiKeyEntity apiKey);

    CompletableFuture<InstitutionApiKeyEntity> updateApiKey(UUID id, InstitutionApiKeyEntity apiKey);

    CompletableFuture<Optional<InstitutionApiKeyEntity>> findApiKeyById(UUID id);

    CompletableFuture<Optional<InstitutionApiKeyEntity>> validateApiKey(String rawApiKey);

    CompletableFuture<List<InstitutionApiKeyEntity>> findApiKeysByInstitutionId(UUID institutionId);

    CompletableFuture<List<InstitutionApiKeyEntity>> findActiveApiKeysByInstitutionId(UUID institutionId);

    CompletableFuture<Void> revokeApiKey(UUID id);

    CompletableFuture<Void> deleteApiKey(UUID id);

}
