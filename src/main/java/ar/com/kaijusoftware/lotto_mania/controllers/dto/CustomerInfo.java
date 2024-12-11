package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CustomerInfo {
    Long id;

    PersonInfo person;

    List<AddressInfo> addresses;

    List<PersonContactInfo> contacts;

}
