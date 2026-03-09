package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reporte crediticio enviado por una institución sobre una persona.
 * Contiene información general del reporte.
 */
public record CreditReportEntity(
        UUID id,
        UUID personId,
        UUID institutionId,
        LocalDateTime reportedAt
) implements Serializable {
}