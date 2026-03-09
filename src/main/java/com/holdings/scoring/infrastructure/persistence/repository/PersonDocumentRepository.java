package com.holdings.scoring.infrastructure.persistence.repository;

import com.holdings.scoring.domain.model.entity.PersonDocumentEntity;
import com.holdings.scoring.domain.model.enums.DocumentType;
import com.holdings.scoring.domain.ports.out.PersonDocumentRepositoryPort;
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
public class PersonDocumentRepository implements PersonDocumentRepositoryPort {

    private final DatabaseClient databaseClient;

    @Override
    public CompletableFuture<PersonDocumentEntity> save(PersonDocumentEntity document) {
        return databaseClient.sql("""
                        INSERT INTO person_documents (id, person_id, document_type, document_number, country, created_at, updated_at)
                        VALUES (:id, :personId, :documentType, :documentNumber, :country, :createdAt, :updatedAt)
                        """)
                .bind("id", document.id())
                .bind("personId", document.personId())
                .bind("documentType", document.documentType().name())
                .bind("documentNumber", document.documentNumber())
                .bind("country", document.country())
                .bind("createdAt", document.createdAt())
                .bind("updatedAt", document.createdAt())
                .fetch()
                .rowsUpdated()
                .thenReturn(document)
                .toFuture();
    }

    @Override
    public CompletableFuture<PersonDocumentEntity> update(PersonDocumentEntity document) {
        return databaseClient.sql("""
                        UPDATE person_documents
                        SET document_type   = :documentType,
                            document_number = :documentNumber,
                            country         = :country,
                            updated_at      = :updatedAt
                        WHERE id = :id
                        """)
                .bind("id", document.id())
                .bind("documentType", document.documentType().name())
                .bind("documentNumber", document.documentNumber())
                .bind("country", document.country())
                .bind("updatedAt", LocalDateTime.now())
                .fetch()
                .rowsUpdated()
                .thenReturn(document)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<PersonDocumentEntity>> findById(UUID id) {
        return databaseClient.sql("SELECT * FROM person_documents WHERE id = :id")
                .bind("id", id)
                .map((row, metadata) -> new PersonDocumentEntity(
                        row.get("id", UUID.class),
                        row.get("person_id", UUID.class),
                        DocumentType.valueOf(row.get("document_type", String.class)),
                        row.get("document_number", String.class),
                        row.get("country", String.class),
                        row.get("created_at", LocalDateTime.class)
                ))
                .one()
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<List<PersonDocumentEntity>> findByPersonId(UUID personId) {
        return databaseClient.sql("SELECT * FROM person_documents WHERE person_id = :personId ORDER BY created_at DESC")
                .bind("personId", personId)
                .map((row, metadata) -> new PersonDocumentEntity(
                        row.get("id", UUID.class),
                        row.get("person_id", UUID.class),
                        DocumentType.valueOf(row.get("document_type", String.class)),
                        row.get("document_number", String.class),
                        row.get("country", String.class),
                        row.get("created_at", LocalDateTime.class)
                ))
                .all()
                .collectList()
                .toFuture();
    }

    @Override
    public CompletableFuture<Void> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM person_documents WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then()
                .toFuture();
    }
}
