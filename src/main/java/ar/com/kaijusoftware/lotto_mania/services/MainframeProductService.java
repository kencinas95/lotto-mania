package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;

public interface MainframeProductService {
    Boolean isProductActivated();

    BasicResult<Boolean> activate(String key, String username, String password);

}
