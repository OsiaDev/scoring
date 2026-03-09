package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Sistema de tokenización utilizado para proteger identificadores sensibles
 * como números de documento o cuentas.
 */
public record DataTokenEntity(
        UUID id,
        String token,
        String originalHash,
        LocalDateTime createdAt
) implements Serializable {
}