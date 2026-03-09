package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.DataAccessLogEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para el registro y auditoría de accesos a datos personales.
 */
public interface DataAccessLogUseCase {

    CompletableFuture<DataAccessLogEntity> logAccess(DataAccessLogEntity log);

    CompletableFuture<Optional<DataAccessLogEntity>> findLogById(UUID id);

    CompletableFuture<List<DataAccessLogEntity>> findLogsByInstitutionId(UUID institutionId);

    CompletableFuture<List<DataAccessLogEntity>> findLogsByPersonId(UUID personId);

    CompletableFuture<Void> deleteLog(UUID id);

}
