package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import ar.com.kaijusoftware.lotto_mania.models.PersonDocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@NotNull
public class PersonDocumentInfo {
    PersonDocumentType type;

    String value;

}
