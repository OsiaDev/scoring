package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.DataAccessLogEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DataAccessLogRepositoryPort {

    CompletableFuture<DataAccessLogEntity> save(DataAccessLogEntity log);

    CompletableFuture<Optional<DataAccessLogEntity>> findById(UUID id);

    CompletableFuture<List<DataAccessLogEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<List<DataAccessLogEntity>> findByPersonId(UUID personId);

    CompletableFuture<Void> deleteById(UUID id);

}
