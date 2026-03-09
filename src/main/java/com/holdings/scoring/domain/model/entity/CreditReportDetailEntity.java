package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Detalle específico de un reporte crediticio.
 * Permite almacenar campos variables enviados por las instituciones.
 */
public record CreditReportDetailEntity(
        UUID id,
        UUID creditReportId,
        String fieldName,
        String fieldValue
) implements Serializable {
}