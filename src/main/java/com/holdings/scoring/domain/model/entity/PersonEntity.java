package com.holdings.scoring.domain.model.entity;

import com.holdings.scoring.domain.model.enums.GenderType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa a una persona registrada en el sistema de scoring.
 * Es la entidad principal sobre la cual se calcula el historial crediticio y el score.
 */
public record PersonEntity(
        UUID id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        GenderType gender,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements Serializable {
}