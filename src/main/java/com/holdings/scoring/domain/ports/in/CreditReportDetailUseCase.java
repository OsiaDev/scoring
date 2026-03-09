package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.CreditReportDetailEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de detalles específicos de un reporte crediticio.
 */
public interface CreditReportDetailUseCase {

    CompletableFuture<CreditReportDetailEntity> createDetail(CreditReportDetailEntity detail);

    CompletableFuture<CreditReportDetailEntity> updateDetail(UUID id, CreditReportDetailEntity detail);

    CompletableFuture<Optional<CreditReportDetailEntity>> findDetailById(UUID id);

    CompletableFuture<List<CreditReportDetailEntity>> findDetailsByCreditReportId(UUID creditReportId);

    CompletableFuture<Void> deleteDetail(UUID id);

    CompletableFuture<Void> deleteDetailsByCreditReportId(UUID creditReportId);

}
