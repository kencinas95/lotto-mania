package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GameInfo {
    String code;

    String name;

    String description;

    String settings;

    List<GameModeInfo> modes;

}
