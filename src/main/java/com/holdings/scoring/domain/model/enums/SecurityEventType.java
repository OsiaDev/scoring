package com.holdings.scoring.domain.model.enums;

import lombok.Getter;

/**
 * Eventos de seguridad registrados por el sistema.
 */
@Getter
public enum SecurityEventType {

    FAILED_LOGIN("Intento de login fallido"),
    INVALID_API_KEY("API Key inválida"),
    RATE_LIMIT("Exceso de solicitudes"),
    SUSPICIOUS_ACTIVITY("Actividad sospechosa");

    private final String label;

    SecurityEventType(String label) {
        this.label = label;
    }

}