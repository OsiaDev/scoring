package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Tipos de documentos de identidad soportados por el sistema.
 */
@Getter
public enum DocumentType {

    CC("Cédula de ciudadanía"),
    CE("Cédula de extranjería"),
    PASSPORT("Pasaporte"),
    NIT("Número de identificación tributaria");

    private final String label;

    DocumentType(String label) {
        this.label = label;
    }

}