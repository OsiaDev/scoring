package com.holdings.scoring.domain.ports.out;

import com.holdings.scoring.domain.model.entity.CreditAccountEntity;
import com.holdings.scoring.domain.model.enums.AccountStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CreditAccountRepositoryPort {

    CompletableFuture<CreditAccountEntity> save(CreditAccountEntity account);

    CompletableFuture<CreditAccountEntity> update(CreditAccountEntity account);

    CompletableFuture<Optional<CreditAccountEntity>> findById(UUID id);

    CompletableFuture<List<CreditAccountEntity>> findByPersonId(UUID personId);

    CompletableFuture<List<CreditAccountEntity>> findByPersonIdAndStatus(UUID personId, AccountStatus status);

    CompletableFuture<List<CreditAccountEntity>> findByInstitutionId(UUID institutionId);

    CompletableFuture<Void> deleteById(UUID id);

}
