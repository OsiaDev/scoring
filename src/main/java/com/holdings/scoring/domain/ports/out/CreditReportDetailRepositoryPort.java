package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.CreditReportDetailEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreditReportDetailRepositoryPort {

    CompletableFuture<CreditReportDetailEntity> save(CreditReportDetailEntity detail);

    CompletableFuture<CreditReportDetailEntity> update(CreditReportDetailEntity detail);

    CompletableFuture<Optional<CreditReportDetailEntity>> findById(UUID id);

    CompletableFuture<List<CreditReportDetailEntity>> findByCreditReportId(UUID creditReportId);

    CompletableFuture<Void> deleteById(UUID id);

    CompletableFuture<Void> deleteByCreditReportId(UUID creditReportId);

}
