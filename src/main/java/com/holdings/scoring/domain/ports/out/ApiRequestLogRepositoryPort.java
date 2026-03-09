package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.ApiRequestLogEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ApiRequestLogRepositoryPort {

    CompletableFuture<ApiRequestLogEntity> save(ApiRequestLogEntity log);

    CompletableFuture<Optional<ApiRequestLogEntity>> findById(UUID id);

    CompletableFuture<List<ApiRequestLogEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteById(UUID id);

}
