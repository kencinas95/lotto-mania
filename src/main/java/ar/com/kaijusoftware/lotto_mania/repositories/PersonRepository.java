package ar.com.kaijusoftware.lotto_mania.repositories;

import ar.com.kaijusoftware.lotto_mania.models.Person;
import ar.com.kaijusoftware.lotto_mania.models.PersonDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.document.type = :docType AND p.document.value = :docValue")
    Optional<Person> getPersonByDocument(@Param("docType") PersonDocumentType docType, @Param("docValue") String docValue);

}
