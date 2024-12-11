package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Value
@Builder
@JsonInclude(Include.NON_NULL)
public class CommerceInfo {
    Long id;

    String name;

    AddressInfo address;

    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime opensAt;

    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime closesAt;

}
