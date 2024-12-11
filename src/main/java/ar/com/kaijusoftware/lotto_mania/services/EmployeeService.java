package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.GameBetInfo;
import ar.com.kaijusoftware.lotto_mania.models.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EmployeeService {
    Mono<Transaction> bet(Long employeeId, Long customerId, Long commerceId, List<GameBetInfo> bets);
}
