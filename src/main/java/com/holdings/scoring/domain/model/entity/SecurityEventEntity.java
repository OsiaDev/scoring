package com.holdings.scoring.domain.model.entity;

import com.holdings.scoring.domain.model.enums.SecurityEventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Eventos de seguridad registrados en el sistema como intentos fallidos de acceso,
 * bloqueos o actividades sospechosas.
 */
public record SecurityEventEntity(
        UUID id,
        SecurityEventType eventType,
        String description,
        String ipAddress,
        LocalDateTime createdAt
) implements Serializable {
}
