package com.holdings.scoring.domain.model.entity;

import com.holdings.scoring.domain.model.enums.AccountStatus;
import com.holdings.scoring.domain.model.enums.AccountType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una cuenta o producto financiero asociado a una persona.
 * Puede ser un préstamo, tarjeta de crédito o cualquier obligación financiera.
 */
public record CreditAccountEntity(
        UUID id,
        UUID personId,
        UUID institutionId,
        AccountType accountType,
        BigDecimal creditLimit,
        BigDecimal balance,
        AccountStatus status,
        LocalDateTime openedAt,
        LocalDateTime closedAt
) implements Serializable {
}