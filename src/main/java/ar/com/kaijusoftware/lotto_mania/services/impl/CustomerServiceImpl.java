package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.models.Customer;
import ar.com.kaijusoftware.lotto_mania.models.PersonDocumentType;
import ar.com.kaijusoftware.lotto_mania.repositories.CustomerRepository;
import ar.com.kaijusoftware.lotto_mania.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Mono<Customer> getCustomerById(Long customerId) {
        log.debug("Getting customer by id: {}", customerId);

        return Mono.justOrEmpty(customerRepository.findById(customerId))
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")));
    }

    @Override
    public Mono<Customer> getCustomerByDocumentation(PersonDocumentType type, String value) {
        log.debug("Getting customer by documentation: {} {}", type.name(), value);

        return Mono.justOrEmpty(customerRepository.getCustomerByDocumentation(type, value))
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")));
    }
}
