package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.ApiRequestLogEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el registro y consulta de solicitudes realizadas a la API del sistema.
 */
public interface ApiRequestLogUseCase {

    CompletableFuture<ApiRequestLogEntity> logRequest(ApiRequestLogEntity log);

    CompletableFuture<Optional<ApiRequestLogEntity>> findLogById(UUID id);

    CompletableFuture<List<ApiRequestLogEntity>> findLogsByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteLog(UUID id);

}
