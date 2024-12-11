package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.GameBetInfo;
import ar.com.kaijusoftware.lotto_mania.models.Transaction;
import ar.com.kaijusoftware.lotto_mania.repositories.CommerceRepository;
import ar.com.kaijusoftware.lotto_mania.repositories.EmployeeRepository;
import ar.com.kaijusoftware.lotto_mania.services.BetService;
import ar.com.kaijusoftware.lotto_mania.services.CustomerService;
import ar.com.kaijusoftware.lotto_mania.services.EmployeeService;
import ar.com.kaijusoftware.lotto_mania.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final CommerceRepository commerceRepository;

    private final CustomerService customerService;

    private final TransactionService transactionService;

    private final BetService betService;

    @Override
    public Mono<Transaction> bet(final Long employeeId,
                                 final Long customerId,
                                 final Long commerceId,
                                 final List<GameBetInfo> bets) {
        log.debug("Creating bet for customer: {}, by employee: {}, in commerce: {}, bets: {}", customerId, employeeId, commerceId, bets);

        return Mono.fromCallable(() -> employeeRepository.findById(employeeId))
                .filter(Optional::isPresent)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found")))
                .map(Optional::get)
                .flatMap(employee -> Mono.fromCallable(() -> commerceRepository.findById(commerceId))
                        .filter(Optional::isPresent)
                        .switchIfEmpty(Mono.error(new RuntimeException("Commerce not found")))
                        .map(Optional::get)
                        .flatMap(commerce -> customerService.getCustomerById(customerId)
                                .flatMap(customer -> transactionService.create(commerce, employee, customer))))
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(transaction -> betService.performBulkBets(transaction, bets));
    }

}
