package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.EncryptionKeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EncryptionKeyRepositoryPort {

    CompletableFuture<EncryptionKeyEntity> save(EncryptionKeyEntity encryptionKey);

    CompletableFuture<EncryptionKeyEntity> update(EncryptionKeyEntity encryptionKey);

    CompletableFuture<Optional<EncryptionKeyEntity>> findById(UUID id);

    CompletableFuture<Optional<EncryptionKeyEntity>> findByKeyAlias(String keyAlias);

    CompletableFuture<Optional<EncryptionKeyEntity>> findActiveByAlgorithm(String algorithm);

    CompletableFuture<List<EncryptionKeyEntity>> findAll();

    CompletableFuture<Void> deleteById(UUID id);

}
