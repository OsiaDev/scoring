package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Historial de estado de una cuenta de crédito.
 * Permite registrar cambios en saldo, mora o estado reportados periódicamente.
 */
public record CreditAccountHistoryEntity(
        UUID id,
        UUID creditAccountId,
        BigDecimal balance,
        Integer daysPastDue,
        String status,
        LocalDateTime reportedAt
) implements Serializable {
}