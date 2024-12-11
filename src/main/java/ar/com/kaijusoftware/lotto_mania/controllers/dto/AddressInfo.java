package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import ar.com.kaijusoftware.lotto_mania.models.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@JsonInclude(NON_NULL)
public class AddressInfo {
    Long id;

    String country;

    String state;

    String city;

    String postalCode;

    String street;

    String numberOrDetail;

    Tag tag;

}
