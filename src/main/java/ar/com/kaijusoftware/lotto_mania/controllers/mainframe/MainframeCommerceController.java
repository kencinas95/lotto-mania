package ar.com.kaijusoftware.lotto_mania.controllers.mainframe;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.AddressInfo;
import ar.com.kaijusoftware.lotto_mania.controllers.dto.CommerceInfo;
import ar.com.kaijusoftware.lotto_mania.models.Address;
import ar.com.kaijusoftware.lotto_mania.models.Commerce;
import ar.com.kaijusoftware.lotto_mania.models.Tag;
import ar.com.kaijusoftware.lotto_mania.repositories.CommerceRepository;
import ar.com.kaijusoftware.lotto_mania.services.MainframeWebService;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mainframe/commerce")
public class MainframeCommerceController {
    private final CommerceRepository commerceRepository;

    private final MainframeWebService mainframeService;

    @PostMapping
    public ResponseEntity<BasicResult<Commerce>> create(final @RequestBody CommerceInfo commerceInfo) {
        log.debug("Creating new commerce: {}", commerceInfo);

        AddressInfo addressInfo = Optional.ofNullable(commerceInfo.getAddress()).orElseThrow();

        Address address = Address.builder()
                .country(addressInfo.getCountry())
                .state(addressInfo.getState())
                .city(addressInfo.getCity())
                .postalCode(addressInfo.getPostalCode())
                .street(addressInfo.getPostalCode())
                .street(addressInfo.getStreet())
                .numberOrDetail(addressInfo.getNumberOrDetail())
                .tag(Tag.COMMERCE)
                .build();

        Commerce commerce = Commerce.builder()
                .name(commerceInfo.getName())
                .address(address)
                .opensAt(commerceInfo.getOpensAt())
                .closesAt(commerceInfo.getClosesAt())
                .build();

        return mainframeService.saveAndSend(commerce, commerceRepository, HttpStatus.CREATED);
    }


}
