package com.holdings.scoring.domain.ports.in;

import com.holdings.scoring.domain.model.entity.PersonAddressEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Casos de uso para la gestión de direcciones registradas de una persona.
 */
public interface PersonAddressUseCase {

    CompletableFuture<PersonAddressEntity> createAddress(PersonAddressEntity address);

    CompletableFuture<PersonAddressEntity> updateAddress(UUID id, PersonAddressEntity address);

    CompletableFuture<Optional<PersonAddressEntity>> findAddressById(UUID id);

    CompletableFuture<List<PersonAddressEntity>> findAddressesByPersonId(UUID personId);

    CompletableFuture<Void> deleteAddress(UUID id);

}
