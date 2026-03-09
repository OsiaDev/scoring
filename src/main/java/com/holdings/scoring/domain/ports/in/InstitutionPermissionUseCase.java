package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.InstitutionPermissionEntity;
import com.holdings.scoring.domain.model.enums.InstitutionPermissionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de permisos asignados a instituciones dentro del sistema.
 */
public interface InstitutionPermissionUseCase {

    CompletableFuture<InstitutionPermissionEntity> grantPermission(InstitutionPermissionEntity permission);

    CompletableFuture<Optional<InstitutionPermissionEntity>> findPermissionById(UUID id);

    CompletableFuture<List<InstitutionPermissionEntity>> findPermissionsByInstitutionId(UUID institutionId);

    CompletableFuture<Boolean> hasPermission(UUID institutionId, InstitutionPermissionType permission);

    CompletableFuture<Void> revokePermission(UUID id);

    CompletableFuture<Void> revokeAllPermissions(UUID institutionId);

}
