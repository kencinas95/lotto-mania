package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.models.Commerce;
import ar.com.kaijusoftware.lotto_mania.models.Customer;
import ar.com.kaijusoftware.lotto_mania.models.Employee;
import ar.com.kaijusoftware.lotto_mania.models.Transaction;
import ar.com.kaijusoftware.lotto_mania.models.TransactionError;
import ar.com.kaijusoftware.lotto_mania.models.TransactionErrorCode;
import ar.com.kaijusoftware.lotto_mania.models.TransactionStatus;
import ar.com.kaijusoftware.lotto_mania.repositories.TransactionErrorRepository;
import ar.com.kaijusoftware.lotto_mania.repositories.TransactionRepository;
import ar.com.kaijusoftware.lotto_mania.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionErrorRepository transactionErrorRepository;

    private void addError(Transaction transaction, String code, String description, String dump) {
        log.debug("A transaction error will be added: {}, {}, {}, {}", transaction, code, description, dump);

        TransactionError error = TransactionError.builder()
                .code(code)
                .description(description)
                .dump(dump != null ? dump.substring(0, Math.min(dump.length(), 2048)) : null)
                .transaction(changeStatus(transaction, TransactionStatus.WITH_ERRORS))
                .build();

        transactionErrorRepository.save(error);

        log.error("New transaction error has been added: {}", error);
    }

    @Override
    public void addError(Transaction transaction, String code) {
        addError(transaction, code, TransactionErrorCode.getErrorDescriptionByCode(code), null);
    }

    @Override
    public void addError(Transaction transaction, String code, Throwable t) {
        addError(transaction, code, TransactionErrorCode.getErrorDescriptionByCode(code), ExceptionUtils.getMessage(t));
    }

    @Override
    public void addAnonymousError(Transaction transaction, String description) {
        addError(transaction, TransactionErrorCode.ANONYMOUS_ERROR, TransactionErrorCode.getErrorDescriptionByCode(TransactionErrorCode.ANONYMOUS_ERROR), null);
    }

    @Override
    public void addAnonymousError(Transaction transaction, Throwable t) {
        addError(transaction, TransactionErrorCode.ANONYMOUS_ERROR, t.getMessage(), ExceptionUtils.getStackTrace(t));
    }

    @Override
    public void addAnonymousError(Transaction transaction, String description, Throwable t) {
        addError(transaction, TransactionErrorCode.ANONYMOUS_ERROR, description, ExceptionUtils.getMessage(t));
    }

    @Override
    public Mono<Transaction> create(Commerce commerce, Employee employee, Customer customer) {
        log.debug("Creating new transaction for: Commerce[{}], Employee[{}], Customer[{}]", commerce, employee, customer);
        Transaction transaction = Transaction.builder()
                .pk(Transaction.generatePk(employee.getPk(), customer.getPk(), commerce.getPk()))
                .commerce(commerce)
                .employee(employee)
                .customer(customer)
                .status(TransactionStatus.PROCESSING)
                .build();
        return Mono.fromCallable(() -> transactionRepository.saveAndFlush(transaction));
    }

    @Override
    public Transaction changeStatus(Transaction transaction, TransactionStatus status) {
        if (!transaction.hasStatus(status)) {
            log.debug("Set new status {} to transaction: {}", status, transaction);

            transaction.setStatus(status);
            return transactionRepository.saveAndFlush(transaction);
        }
        return transaction;
    }
}
