package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@JsonInclude(NON_NULL)
public class PersonInfo {
    Long id;

    @NotBlank
    String firstname;

    @NotBlank
    String surname;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;

    @NotNull
    PersonDocumentInfo document;

}
