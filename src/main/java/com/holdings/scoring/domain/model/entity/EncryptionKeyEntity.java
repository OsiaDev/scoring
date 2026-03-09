package com.holdings.scoring.domain.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una clave de cifrado utilizada para proteger datos sensibles del sistema.
 */
public record EncryptionKeyEntity(
        UUID id,
        String keyAlias,
        String algorithm,
        Boolean active,
        LocalDateTime createdAt
) implements Serializable {
}