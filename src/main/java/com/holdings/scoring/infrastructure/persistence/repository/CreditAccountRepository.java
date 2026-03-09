package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.CreditAccountEntity;
import com.holdings.scoring.domain.model.enums.AccountStatus;
import com.holdings.scoring.domain.model.enums.AccountType;
import com.holdings.scoring.domain.ports.out.CreditAccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class CreditAccountRepository implements CreditAccountRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<CreditAccountEntity> save(CreditAccountEntity account) {
        return databaseClient.sql("""
                        INSERT INTO credit_accounts
                            (id, person_id, institution_id, account_type, credit_limit, balance, status, opened_at, closed_at, created_at, updated_at)
                        VALUES
                            (:id, :personId, :institutionId, :accountType, :creditLimit, :balance, :status, :openedAt, :closedAt, :createdAt, :updatedAt)
                        """)
                .bind("id", account.id())
                .bind("personId", account.personId())
                .bind("institutionId", account.institutionId())
                .bind("accountType", account.accountType().name())
                .bind("creditLimit", account.creditLimit())
                .bind("balance", account.balance())
                .bind("status", account.status().name())
                .bind("openedAt", account.openedAt())
                .bindNull("closedAt", LocalDateTime.class)
                .bind("createdAt", account.openedAt())
                .bind("updatedAt", account.openedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(account)
                .toFuture();
    }

    @Override
    public CompletableFuture<CreditAccountEntity> update(CreditAccountEntity account) {
        return databaseClient.sql("""
                        UPDATE credit_accounts
                        SET account_type  = :accountType,
                            credit_limit  = :creditLimit,
                            balance       = :balance,
                            status        = :status,
                            closed_at     = :closedAt,
                            updated_at    = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", account.id())
                .bind("accountType", account.accountType().name())
                .bind("creditLimit", account.creditLimit())
                .bind("balance", account.balance())
                .bind("status", account.status().name())
                .bind("closedAt", account.closedAt())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(account)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<CreditAccountEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM credit_accounts WHERE id = :id")
                .bind("id", id)
                .map(this::mapRow)
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditAccountEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM credit_accounts WHERE person_id = :personId ORDER BY opened_at DESC")
                .bind("personId", personId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditAccountEntity>> findByPersonIdAndStatus(UUID personId, AccountStatus status) {
        return databaseClient.sql("SELECT * FROM credit_accounts WHERE person_id = :personId AND status = :status ORDER BY opened_at DESC")
                .bind("personId", personId)
                .bind("status", status.name())
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<List<CreditAccountEntity>> findByInstitutionId(UUID institutionId) {
        return databaseClient.sql("SELECT * FROM credit_accounts WHERE institution_id = :institutionId ORDER BY opened_at DESC")
                .bind("institutionId", institutionId)
                .map(this::mapRow)
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM credit_accounts WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }

    private CreditAccountEntity mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata metadata) {
        return new CreditAccountEntity(
                row.get("id", UUID.class),
                row.get("person_id", UUID.class),
                row.get("institution_id", UUID.class),
                AccountType.valueOf(row.get("account_type", String.class)),
                row.get("credit_limit", BigDecimal.class),
                row.get("balance", BigDecimal.class),
                AccountStatus.valueOf(row.get("status", String.class)),
                row.get("opened_at", LocalDateTime.class),
                row.get("closed_at", LocalDateTime.class)
        );
    }
}
