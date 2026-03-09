package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Método utilizado para realizar un pago sobre una obligación financiera.
 */
@Getter
public enum PaymentMethod {

    BANK_TRANSFER("Transferencia bancaria"),
    CASH("Efectivo"),
    CARD("Tarjeta"),
    PSE("Pago PSE"),
    OTHER("Otro");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }

}