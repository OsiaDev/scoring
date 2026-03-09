package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una institución o empresa autorizada para consultar o reportar información crediticia.
 * Puede ser un banco, fintech, cooperativa o aplicación financiera.
 */
public record InstitutionEntity(
        UUID id,
        String name,
        String taxId,
        String country,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements Serializable {
}