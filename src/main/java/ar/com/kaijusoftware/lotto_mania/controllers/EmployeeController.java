package ar.com.kaijusoftware.lotto_mania.controllers;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.BetBundle;
import ar.com.kaijusoftware.lotto_mania.controllers.dto.TransactionInfo;
import ar.com.kaijusoftware.lotto_mania.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/bet")
    public Mono<ResponseEntity<TransactionInfo>> bet(final @RequestBody BetBundle bundle) {
        log.debug("bet with bundle: {}", bundle);

        return employeeService.bet(bundle.getEmployee(), bundle.getCustomer(), bundle.getCommerce(), bundle.getBets())
                .map(transaction -> TransactionInfo.builder()
                        .id(transaction.getPk())
                        .status(transaction.getStatus())
                        .build())
                .map(transaction -> ResponseEntity.status(201).body(transaction));
    }

    /* public Mono<ResponseEntity<TransactionInfo>> pay() {
        log.debug("pay");
    }*/

}
