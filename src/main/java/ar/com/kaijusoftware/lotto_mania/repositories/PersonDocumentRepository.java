package ar.com.kaijusoftware.lotto_mania.repositories;

import ar.com.kaijusoftware.lotto_mania.models.PersonDocument;
import ar.com.kaijusoftware.lotto_mania.models.PersonDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDocumentRepository extends JpaRepository<PersonDocument, Long> {
    Optional<PersonDocument> findByTypeAndValue(PersonDocumentType type, String value);
}
