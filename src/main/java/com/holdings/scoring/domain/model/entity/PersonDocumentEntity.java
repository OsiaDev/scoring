package com.holdings.scoring.domain.model.entity;

import com.holdings.scoring.domain.model.enums.DocumentType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Almacena los documentos de identidad asociados a una persona.
 * Permite soportar múltiples tipos de documento como cédula, pasaporte o NIT.
 */
public record PersonDocumentEntity(
        UUID id,
        UUID personId,
        DocumentType documentType,
        String documentNumber,
        String country,
        LocalDateTime createdAt
) implements Serializable {
}