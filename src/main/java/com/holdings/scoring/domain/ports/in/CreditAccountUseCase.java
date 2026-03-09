package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.CreditAccountEntity;
import com.holdings.scoring.domain.model.enums.AccountStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de cuentas y productos financieros asociados a una persona.
 */
public interface CreditAccountUseCase {

    CompletableFuture<CreditAccountEntity> createAccount(CreditAccountEntity account);

    CompletableFuture<CreditAccountEntity> updateAccount(UUID id, CreditAccountEntity account);

    CompletableFuture<Optional<CreditAccountEntity>> findAccountById(UUID id);

    CompletableFuture<List<CreditAccountEntity>> findAccountsByPersonId(UUID personId);

    CompletableFuture<List<CreditAccountEntity>> findAccountsByPersonIdAndStatus(UUID personId, AccountStatus status);

    CompletableFuture<List<CreditAccountEntity>> findAccountsByInstitutionId(UUID institutionId);

    CompletableFuture<Void> closeAccount(UUID id);

    CompletableFuture<Void> deleteAccount(UUID id);

}
