package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Factores que influyeron en el cálculo de un score específico.
 * Se utiliza para explicar o auditar el resultado del scoring.
 */
public record ScoreFactorEntity(
        UUID id,
        UUID creditScoreId,
        String factor,
        BigDecimal weight,
        String description
) implements Serializable {
}