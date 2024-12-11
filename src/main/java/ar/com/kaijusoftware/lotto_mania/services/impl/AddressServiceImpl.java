package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.AddressInfo;
import ar.com.kaijusoftware.lotto_mania.models.Address;
import ar.com.kaijusoftware.lotto_mania.repositories.AddressRepository;
import ar.com.kaijusoftware.lotto_mania.services.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public Address getOrCreate(AddressInfo addressInfo) {
        Address address = Address.builder()
                .country(addressInfo.getCountry())
                .state(addressInfo.getState())
                .city(addressInfo.getCity())
                .street(addressInfo.getStreet())
                .postalCode(addressInfo.getPostalCode())
                .numberOrDetail(addressInfo.getNumberOrDetail())
                .build();

        return addressRepository.findOne(Example.of(address))
                .orElseGet(() -> addressRepository.saveAndFlush(address.withTag(addressInfo.getTag())));
    }
}
