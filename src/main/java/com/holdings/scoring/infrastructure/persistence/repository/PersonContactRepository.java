package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.PersonContactEntity;
import com.holdings.scoring.domain.ports.out.PersonContactRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class PersonContactRepository implements PersonContactRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<PersonContactEntity> save(PersonContactEntity contact) {
        return databaseClient.sql("""
                        INSERT INTO person_contacts (id, person_id, email, phone_number, created_at, updated_at)
                        VALUES (:id, :personId, :email, :phoneNumber, :createdAt, :updatedAt)
                        """)
                .bind("id", contact.id())
                .bind("personId", contact.personId())
                .bind("email", contact.email())
                .bind("phoneNumber", contact.phoneNumber())
                .bind("createdAt", contact.createdAt())
                .bind("updatedAt", contact.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(contact)
                .toFuture();
    }

    @Override
    public CompletableFuture<PersonContactEntity> update(PersonContactEntity contact) {
        return databaseClient.sql("""
                        UPDATE person_contacts
                        SET email        = :email,
                            phone_number = :phoneNumber,
                            updated_at   = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", contact.id())
                .bind("email", contact.email())
                .bind("phoneNumber", contact.phoneNumber())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(contact)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<PersonContactEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM person_contacts WHERE id = :id")
                .bind("id", id)
                .map((row, metadata) -> new PersonContactEntity(
                        row.get("id", UUID.class),
                        row.get("person_id", UUID.class),
                        row.get("email", String.class),
                        row.get("phone_number", String.class),
                        row.get("created_at", LocalDateTime.class)
                ))
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<PersonContactEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM person_contacts WHERE person_id = :personId ORDER BY created_at DESC")
                .bind("personId", personId)
                .map((row, metadata) -> new PersonContactEntity(
                        row.get("id", UUID.class),
                        row.get("person_id", UUID.class),
                        row.get("email", String.class),
                        row.get("phone_number", String.class),
                        row.get("created_at", LocalDateTime.class)
                ))
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM person_contacts WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }
}
