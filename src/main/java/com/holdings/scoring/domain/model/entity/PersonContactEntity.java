package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Información de contacto de la persona como correo electrónico o número telefónico.
 */
public record PersonContactEntity(
        UUID id,
        UUID personId,
        String email,
        String phoneNumber,
        LocalDateTime createdAt
) implements Serializable {
}