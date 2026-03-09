package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Permisos que una institución puede tener dentro del sistema de scoring.
 */
@Getter
public enum InstitutionPermissionType {

    READ_SCORE("Consultar score"),
    REPORT_CREDIT("Reportar crédito"),
    UPDATE_CREDIT("Actualizar información crediticia"),
    READ_REPORT("Consultar reporte crediticio");

    private final String label;

    InstitutionPermissionType(String label) {
        this.label = label;
    }

}