package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.PersonInfo;
import ar.com.kaijusoftware.lotto_mania.models.Person;

public interface PersonService {
    Person getOrCreate(PersonInfo peronInfo);
}