package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Usuarios internos pertenecientes a una institución autorizada.
 * Permite gestionar acceso administrativo dentro de la plataforma.
 */
public record InstitutionUserEntity(
        UUID id,
        UUID institutionId,
        String email,
        String passwordHash,
        String role,
        Boolean active,
        LocalDateTime createdAt
) implements Serializable {
}