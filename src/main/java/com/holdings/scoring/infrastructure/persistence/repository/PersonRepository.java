package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.PersonEntity;
import com.holdings.scoring.domain.model.enums.GenderType;
import com.holdings.scoring.domain.ports.out.PersonRepositoryPort;
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
public class PersonRepository implements PersonRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<PersonEntity> save(PersonEntity person) {
        return databaseClient.sql("""
                        INSERT INTO persons (id, first_name, last_name, birth_date, gender, created_at, updated_at)
                        VALUES (:id, :firstName, :lastName, :birthDate, :gender, :createdAt, :updatedAt)
                        """)
                .bind("id", person.id())
                .bind("firstName", person.firstName())
                .bind("lastName", person.lastName())
                .bind("birthDate", person.birthDate())
                .bind("gender", person.gender().name())
                .bind("createdAt", person.createdAt())
                .bind("updatedAt", person.updatedAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(person)
                .toFuture();
    }

    @Override
    public CompletableFuture<PersonEntity> update(PersonEntity person) {
        return databaseClient.sql("""
                        UPDATE persons
                        SET first_name = :firstName,
                            last_name  = :lastName,
                            birth_date = :birthDate,
                            gender     = :gender,
                            updated_at = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", person.id())
                .bind("firstName", person.firstName())
                .bind("lastName", person.lastName())
                .bind("birthDate", person.birthDate())
                .bind("gender", person.gender().name())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(person)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<PersonEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM persons WHERE id = :id")
                .bind("id", id)
                .map((row, metadata) -> new PersonEntity(
                        row.get("id", UUID.class),
                        row.get("first_name", String.class),
                        row.get("last_name", String.class),
                        row.get("birth_date", java.time.LocalDate.class),
                        GenderType.valueOf(row.get("gender", String.class)),
                        row.get("created_at", LocalDateTime.class),
                        row.get("updated_at", LocalDateTime.class)
                ))
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<PersonEntity>> findAll() {
        return databaseClient.sql("SELECT * FROM persons ORDER BY created_at DESC")
                .map((row, metadata) -> new PersonEntity(
                        row.get("id", UUID.class),
                        row.get("first_name", String.class),
                        row.get("last_name", String.class),
                        row.get("birth_date", java.time.LocalDate.class),
                        GenderType.valueOf(row.get("gender", String.class)),
                        row.get("created_at", LocalDateTime.class),
                        row.get("updated_at", LocalDateTime.class)
                ))
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM persons WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }
}
