package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.InstitutionPermissionEntity;
import com.holdings.scoring.domain.model.enums.InstitutionPermissionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface InstitutionPermissionRepositoryPort {

    CompletableFuture<InstitutionPermissionEntity> save(InstitutionPermissionEntity permission);

    CompletableFuture<Optional<InstitutionPermissionEntity>> findById(UUID id);

    CompletableFuture<List<InstitutionPermissionEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<Boolean> existsByInstitutionIdAndPermission(UUID institutionId, InstitutionPermissionType permission);

    CompletableFuture<Void> deleteById(UUID id);

    CompletableFuture<Void> deleteByInstitutionId(UUID institutionId);

}
