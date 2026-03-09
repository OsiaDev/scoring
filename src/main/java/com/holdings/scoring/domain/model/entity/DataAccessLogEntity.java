package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Registro de accesos a datos personales por parte de instituciones.
 * Es fundamental para auditoría y cumplimiento normativo.
 */
public record DataAccessLogEntity(
        UUID id,
        UUID institutionId,
        UUID personId,
        String action,
        LocalDateTime accessedAt
) implements Serializable {
}
