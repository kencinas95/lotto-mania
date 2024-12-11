package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeProfileInfo {
    String code;

    String description;

}
