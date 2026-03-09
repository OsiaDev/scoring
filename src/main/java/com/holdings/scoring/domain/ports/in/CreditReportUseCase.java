package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.CreditReportEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de reportes crediticios enviados por instituciones.
 */
public interface CreditReportUseCase {

    CompletableFuture<CreditReportEntity> createReport(CreditReportEntity report);

    CompletableFuture<Optional<CreditReportEntity>> findReportById(UUID id);

    CompletableFuture<List<CreditReportEntity>> findReportsByPersonId(UUID personId);

    CompletableFuture<List<CreditReportEntity>> findReportsByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteReport(UUID id);

}
