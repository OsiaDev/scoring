package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.PersonAddressEntity;
import com.holdings.scoring.domain.ports.out.PersonAddressRepositoryPort;
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
public class PersonAddressRepository implements PersonAddressRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<PersonAddressEntity> save(PersonAddressEntity address) {
        return databaseClient.sql("""
                        INSERT INTO person_addresses (id, person_id, address_line, city, state, country, postal_code, created_at, updated_at)
                        VALUES (:id, :personId, :addressLine, :city, :state, :country, :postalCode, :createdAt, :updatedAt)
                        """)
                .bind("id", address.id())
                .bind("personId", address.personId())
                .bind("addressLine", address.addressLine())
                .bind("city", address.city())
                .bind("state", address.state())
                .bind("country", address.country())
                .bind("postalCode", address.postalCode())
                .bind("createdAt", address.createdAt())
                .bind("updatedAt", address.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(address)
                .toFuture();
    }

    @Override
    public CompletableFuture<PersonAddressEntity> update(PersonAddressEntity address) {
        return databaseClient.sql("""
                        UPDATE person_addresses
                        SET address_line = :addressLine,
                            city         = :city,
                            state        = :state,
                            country      = :country,
                            postal_code  = :postalCode,
                            updated_at   = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", address.id())
                .bind("addressLine", address.addressLine())
                .bind("city", address.city())
                .bind("state", address.state())
                .bind("country", address.country())
                .bind("postalCode", address.postalCode())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(address)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<PersonAddressEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM person_addresses WHERE id = :id")
                .bind("id", id)
                .map((row, metadata) -> new PersonAddressEntity(
                        row.get("id", UUID.class),
                        row.get("person_id", UUID.class),
                        row.get("address_line", String.class),
                        row.get("city", String.class),
                        row.get("state", String.class),
                        row.get("country", String.class),
                        row.get("postal_code", String.class),
                        row.get("created_at", LocalDateTime.class)
                ))
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<PersonAddressEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM person_addresses WHERE person_id = :personId ORDER BY created_at DESC")
                .bind("personId", personId)
                .map((row, metadata) -> new PersonAddressEntity(
                        row.get("id", UUID.class),
                        row.get("person_id", UUID.class),
                        row.get("address_line", String.class),
                        row.get("city", String.class),
                        row.get("state", String.class),
                        row.get("country", String.class),
                        row.get("postal_code", String.class),
                        row.get("created_at", LocalDateTime.class)
                ))
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM person_addresses WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }
}
