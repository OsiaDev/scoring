package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Representa el género registrado de la persona.
 */
@Getter
public enum GenderType {

    MALE("Masculino"),
    FEMALE("Femenino"),
    OTHER("Otro"),
    UNKNOWN("Desconocido");

    private final String label;

    GenderType(String label) {
        this.label = label;
    }

}