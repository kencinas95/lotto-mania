package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class GamePlayInfo {
    Long id;

    String externalId;

    GameModeInfo mode;

    LocalDateTime executeAt;

    LocalDate expiresAt;

    LocalDateTime createdAt;

    List<GamePlayResultInfo> results;

}
