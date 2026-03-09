package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Registro de todas las solicitudes realizadas a la API del sistema.
 * Permite auditoría y monitoreo de uso de la plataforma.
 */
public record ApiRequestLogEntity(
        UUID id,
        UUID institutionId,
        String endpoint,
        String method,
        String ipAddress,
        Integer responseStatus,
        LocalDateTime requestedAt
) implements Serializable {
}
