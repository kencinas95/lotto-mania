package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import ar.com.kaijusoftware.lotto_mania.models.CustomerContactType;
import ar.com.kaijusoftware.lotto_mania.models.Tag;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonContactInfo {
    CustomerContactType type;

    String value;

    Boolean isMain;

    Boolean isActive;

    Tag tag;

}
