package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.PersonInfo;
import ar.com.kaijusoftware.lotto_mania.models.Person;
import ar.com.kaijusoftware.lotto_mania.models.PersonDocument;
import ar.com.kaijusoftware.lotto_mania.models.PersonDocumentType;
import ar.com.kaijusoftware.lotto_mania.repositories.PersonDocumentRepository;
import ar.com.kaijusoftware.lotto_mania.repositories.PersonRepository;
import ar.com.kaijusoftware.lotto_mania.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonDocumentRepository personDocumentRepository;

    @Override
    public Person getOrCreate(PersonInfo personInfo) {
        log.debug("Get or create person: {}", personInfo);

        PersonDocument document = Optional.ofNullable(personInfo.getDocument())
                .map(info -> getOrCreateDocument(info.getType(), info.getValue()))
                .orElseThrow(() -> new NoSuchElementException("Couldn't find or create document."));

        return personRepository.getPersonByDocument(document.getType(), document.getValue())
                .orElseGet(() -> personRepository.saveAndFlush(
                        Person.builder()
                                .firstname(personInfo.getFirstname())
                                .surname(personInfo.getSurname())
                                .dob(personInfo.getDob())
                                .document(document)
                                .build()));
    }

    private PersonDocument getOrCreateDocument(PersonDocumentType type, String value) {
        log.debug("Get or create document for type {}: {}", type.name(), value);

        return personDocumentRepository.findByTypeAndValue(type, value)
                .orElseGet(() -> personDocumentRepository.saveAndFlush(
                        PersonDocument.builder()
                                .type(type)
                                .value(value)
                                .build()));
    }
}
