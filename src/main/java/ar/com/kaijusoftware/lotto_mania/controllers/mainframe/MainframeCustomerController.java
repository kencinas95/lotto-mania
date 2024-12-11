package ar.com.kaijusoftware.lotto_mania.controllers.mainframe;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.CustomerInfo;
import ar.com.kaijusoftware.lotto_mania.models.Address;
import ar.com.kaijusoftware.lotto_mania.models.Customer;
import ar.com.kaijusoftware.lotto_mania.models.Person;
import ar.com.kaijusoftware.lotto_mania.repositories.CustomerRepository;
import ar.com.kaijusoftware.lotto_mania.services.AddressService;
import ar.com.kaijusoftware.lotto_mania.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mainframe/customer")
public class MainframeCustomerController {
    private final PersonService personService;
    private final AddressService addressService;
    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<Long> create(final @RequestBody CustomerInfo customerInfo) {
        log.debug("Creating new customer: {}", customerInfo);

        Person person = Optional.ofNullable(customerInfo.getPerson())
                .map(personService::getOrCreate)
                .orElseThrow();

        Set<Address> addresses = Optional.ofNullable(customerInfo.getAddresses())
                .filter(infos -> !infos.isEmpty())
                .map(infos -> infos.stream()
                        .map(addressService::getOrCreate)
                        .collect(Collectors.toSet()))
                .orElseThrow();

        Customer customer = Customer.builder()
                .person(person)
                .addresses(addresses)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(customerRepository.saveAndFlush(customer).getPk());
    }

}
