package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BetBundle {
    Long employee;

    Long customer;

    Long commerce;

    List<GameBetInfo> bets;

}