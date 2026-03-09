package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.InstitutionUserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de usuarios internos pertenecientes a una institución.
 */
public interface InstitutionUserUseCase {

    CompletableFuture<InstitutionUserEntity> createUser(InstitutionUserEntity user);

    CompletableFuture<InstitutionUserEntity> updateUser(UUID id, InstitutionUserEntity user);

    CompletableFuture<Optional<InstitutionUserEntity>> findUserById(UUID id);

    CompletableFuture<Optional<InstitutionUserEntity>> findUserByEmail(String email);

    CompletableFuture<List<InstitutionUserEntity>> findUsersByInstitutionId(UUID institutionId);

    CompletableFuture<List<InstitutionUserEntity>> findActiveUsersByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deactivateUser(UUID id);

    CompletableFuture<Void> deleteUser(UUID id);

}
