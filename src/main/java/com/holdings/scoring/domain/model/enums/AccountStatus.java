package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Estado actual de una cuenta de crédito.
 */
@Getter
public enum AccountStatus {

    ACTIVE("Activo"),
    CLOSED("Cerrado"),
    DEFAULTED("En mora"),
    WRITTEN_OFF("Castigado");

    private final String label;

    AccountStatus(String label) {
        this.label = label;
    }

}