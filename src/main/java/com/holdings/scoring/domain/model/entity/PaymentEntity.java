package com.holdings.scoring.domain.model.entity;

import com.holdings.scoring.domain.model.enums.PaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa un pago realizado por la persona sobre una obligación financiera.
 */
public record PaymentEntity(
        UUID id,
        UUID creditAccountId,
        BigDecimal amount,
        LocalDate paymentDate,
        PaymentMethod paymentMethod,
        LocalDateTime createdAt
) implements Serializable {
}