package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Credenciales de acceso para que una institución pueda consumir la API del sistema de scoring.
 * La clave se almacena de forma segura mediante hash.
 */
public record InstitutionApiKeyEntity(
        UUID id,
        UUID institutionId,
        String apiKeyHash,
        LocalDateTime expiresAt,
        Boolean active,
        LocalDateTime createdAt
) implements Serializable {
}