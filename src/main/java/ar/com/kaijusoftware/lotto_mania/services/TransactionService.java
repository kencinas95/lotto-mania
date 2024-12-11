package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.models.Commerce;
import ar.com.kaijusoftware.lotto_mania.models.Customer;
import ar.com.kaijusoftware.lotto_mania.models.Employee;
import ar.com.kaijusoftware.lotto_mania.models.Transaction;
import ar.com.kaijusoftware.lotto_mania.models.TransactionStatus;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> create(Commerce commerce, Employee employee, Customer customer);

    Transaction changeStatus(Transaction transaction, TransactionStatus status);

    void addError(Transaction transaction, String code);

    void addError(Transaction transaction, String code, Throwable t);

    void addAnonymousError(Transaction transaction, String description);

    void addAnonymousError(Transaction transaction, Throwable t);

    void addAnonymousError(Transaction transaction, String description, Throwable t);


}
