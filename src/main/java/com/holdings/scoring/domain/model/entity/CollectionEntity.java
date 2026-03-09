package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una deuda que ha sido transferida a un proceso de cobranza.
 */
public record CollectionEntity(
        UUID id,
        UUID creditAccountId,
        String agencyName,
        BigDecimal amount,
        LocalDateTime assignedAt
) implements Serializable {
}