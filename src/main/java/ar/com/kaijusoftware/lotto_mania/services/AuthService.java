package ar.com.kaijusoftware.lotto_mania.services;

import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;

import java.util.Map;

public interface AuthService {
    String createAuthToken(String signature, Map<String, ?> claims);

    BasicResult<Boolean> isValidSession(String sid);

}
