package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Historial de scores calculados para una persona a lo largo del tiempo.
 */
public record ScoreHistoryEntity(
        UUID id,
        UUID personId,
        Integer score,
        LocalDateTime calculatedAt
) implements Serializable {
}