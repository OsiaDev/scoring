package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Registro del consentimiento del titular de los datos para que una institución consulte su información.
 * Es fundamental para cumplir con regulaciones de protección de datos como Habeas Data.
 */
public record ConsentEntity(
        UUID id,
        UUID personId,
        UUID institutionId,
        Boolean granted,
        String source,
        LocalDateTime grantedAt,
        LocalDateTime revokedAt
) implements Serializable {
}