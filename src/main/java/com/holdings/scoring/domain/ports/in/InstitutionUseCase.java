package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.InstitutionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de instituciones autorizadas en el sistema de scoring.
 */
public interface InstitutionUseCase {

    CompletableFuture<InstitutionEntity> createInstitution(InstitutionEntity institution);

    CompletableFuture<InstitutionEntity> updateInstitution(UUID id, InstitutionEntity institution);

    CompletableFuture<Optional<InstitutionEntity>> findInstitutionById(UUID id);

    CompletableFuture<Optional<InstitutionEntity>> findInstitutionByTaxId(String taxId);

    CompletableFuture<List<InstitutionEntity>> findAllInstitutions();

    CompletableFuture<List<InstitutionEntity>> findAllActiveInstitutions();

    CompletableFuture<Void> deleteInstitution(UUID id);

}
