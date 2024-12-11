package ar.com.kaijusoftware.lotto_mania.controllers.dto;

import ar.com.kaijusoftware.lotto_mania.models.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionInfo {
    String id;

    EmployeeInfo employee;

    CustomerInfo customer;

    CommerceInfo commerce;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    TransactionStatus status;

    List<GameBetInfo> bets;

}
