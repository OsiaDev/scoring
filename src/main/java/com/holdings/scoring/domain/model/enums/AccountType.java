package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Tipos de productos financieros reportados al sistema de scoring.
 */
@Getter
public enum AccountType {

    PERSONAL_LOAN("Préstamo personal"),
    CREDIT_CARD("Tarjeta de crédito"),
    MICROCREDIT("Microcrédito"),
    MORTGAGE("Crédito hipotecario"),
    VEHICLE_LOAN("Crédito vehicular");

    private final String label;

    AccountType(String label) {
        this.label = label;
    }

}