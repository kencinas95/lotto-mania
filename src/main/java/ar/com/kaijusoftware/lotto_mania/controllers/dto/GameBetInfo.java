package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class GameBetInfo {
    Long gamePlayId;

    String play;

    BigDecimal amount;

    LocalDateTime createdAt;

}
