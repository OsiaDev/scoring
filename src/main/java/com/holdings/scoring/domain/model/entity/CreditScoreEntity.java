package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Resultado del cálculo del score crediticio para una persona.
 */
public record CreditScoreEntity(
        UUID id,
        UUID personId,
        UUID modelId,
        Integer score,
        LocalDateTime calculatedAt
) implements Serializable {
}