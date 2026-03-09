package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.EncryptionKeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de claves de cifrado utilizadas para proteger datos sensibles.
 */
public interface EncryptionKeyUseCase {

    CompletableFuture<EncryptionKeyEntity> createEncryptionKey(EncryptionKeyEntity encryptionKey);

    CompletableFuture<EncryptionKeyEntity> updateEncryptionKey(UUID id, EncryptionKeyEntity encryptionKey);

    CompletableFuture<Optional<EncryptionKeyEntity>> findEncryptionKeyById(UUID id);

    CompletableFuture<Optional<EncryptionKeyEntity>> findEncryptionKeyByAlias(String keyAlias);

    CompletableFuture<Optional<EncryptionKeyEntity>> findActiveEncryptionKeyByAlgorithm(String algorithm);

    CompletableFuture<List<EncryptionKeyEntity>> findAllEncryptionKeys();

    CompletableFuture<Void> rotateEncryptionKey(UUID id);

    CompletableFuture<Void> deleteEncryptionKey(UUID id);

}
