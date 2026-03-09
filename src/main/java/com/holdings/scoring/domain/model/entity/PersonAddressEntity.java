package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Dirección registrada de la persona, utilizada para información geográfica o verificación.
 */
public record PersonAddressEntity(
        UUID id,
        UUID personId,
        String addressLine,
        String city,
        String state,
        String country,
        String postalCode,
        LocalDateTime createdAt
) implements Serializable {
}