package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.models.Customer;
import ar.com.kaijusoftware.lotto_mania.models.PersonDocumentType;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> getCustomerById(Long customerId);

    Mono<Customer> getCustomerByDocumentation(PersonDocumentType type, String value);

}
