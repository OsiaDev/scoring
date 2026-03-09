package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Registro de eventos de mora o incumplimiento en una obligación financiera.
 */
public record DelinquencyEntity(
        UUID id,
        UUID creditAccountId,
        Integer daysPastDue,
        BigDecimal amount,
        LocalDateTime reportedAt
) implements Serializable {
}