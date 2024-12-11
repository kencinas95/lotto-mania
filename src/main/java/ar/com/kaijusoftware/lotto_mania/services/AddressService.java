package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.AddressInfo;
import ar.com.kaijusoftware.lotto_mania.models.Address;

public interface AddressService {
    Address getOrCreate(AddressInfo addressInfo);
}
