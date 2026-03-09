package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Define un modelo de cálculo de score crediticio.
 * Permite versionar distintos algoritmos de scoring.
 */
public record ScoreModelEntity(
        UUID id,
        String name,
        String version,
        Boolean active,
        LocalDateTime createdAt
) implements Serializable {
}