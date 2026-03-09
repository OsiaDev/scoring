package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.CreditReportEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreditReportRepositoryPort {

    CompletableFuture<CreditReportEntity> save(CreditReportEntity report);

    CompletableFuture<Optional<CreditReportEntity>> findById(UUID id);

    CompletableFuture<List<CreditReportEntity>> findByPersonId(UUID personId);

    CompletableFuture<List<CreditReportEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteById(UUID id);

}
