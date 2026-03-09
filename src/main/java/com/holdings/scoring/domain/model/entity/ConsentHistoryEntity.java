package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Historial de cambios del consentimiento otorgado por la persona.
 * Permite auditar cuándo fue otorgado, modificado o revocado.
 */
public record ConsentHistoryEntity(
        UUID id,
        UUID consentId,
        Boolean status,
        String reason,
        LocalDateTime createdAt
) implements Serializable {
}