package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.BetBundle;
import ar.com.kaijusoftware.lotto_mania.services.UserService;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public BasicResult<ResponseCookie> login(String username, String password) {
        return null;
    }

    @Override
    public BasicResult<Boolean> isSessionValid(String rawSessionJWT) {
        return null;
    }

    @Override
    public BasicResult<String> betting(BetBundle bundle) {
        return null;
    }
}
