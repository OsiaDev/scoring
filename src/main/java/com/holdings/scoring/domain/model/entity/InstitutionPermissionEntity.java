package com.holdings.scoring.domain.model.entity;

import com.holdings.scoring.domain.model.enums.InstitutionPermissionType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Define los permisos que posee una institución dentro del sistema.
 * Por ejemplo: consultar score, reportar deuda o actualizar información.
 */
public record InstitutionPermissionEntity(
        UUID id,
        UUID institutionId,
        InstitutionPermissionType permission,
        LocalDateTime createdAt
) implements Serializable {
}