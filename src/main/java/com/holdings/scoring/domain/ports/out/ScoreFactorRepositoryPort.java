package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.ScoreFactorEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ScoreFactorRepositoryPort {

    CompletableFuture<ScoreFactorEntity> save(ScoreFactorEntity factor);

    CompletableFuture<ScoreFactorEntity> update(ScoreFactorEntity factor);

    CompletableFuture<Optional<ScoreFactorEntity>> findById(UUID id);

    CompletableFuture<List<ScoreFactorEntity>> findByCreditScoreId(UUID creditScoreId);

    CompletableFuture<Void> deleteById(UUID id);

    CompletableFuture<Void> deleteByCreditScoreId(UUID creditScoreId);

}
