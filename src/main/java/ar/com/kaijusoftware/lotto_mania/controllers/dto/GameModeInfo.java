package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameModeInfo {
    Long id;

    String code;

    String description;

    GameInfo game;

}
