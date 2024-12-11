package ar.com.kaijusoftware.lotto_mania.services;

import java.security.Key;

import ar.com.kaijusoftware.lotto_mania.controllers.dto.BetBundle;
import ar.com.kaijusoftware.lotto_mania.models.UserSession;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import org.springframework.http.ResponseCookie;

public interface UserService {

    /**
     * Login the user by given a username and a password.
     *
     * @param username: username
     * @param password: password
     * @return BasicResult of UserSession
     */
    BasicResult<ResponseCookie> login(String username, String password);

    /**
     * Checks if the given jwt is a valid session.
     *
     *
     * @param rawSessionJWT: raw session JWT
     * @return BasicResult of boolean
     */
    BasicResult<Boolean> isSessionValid(String rawSessionJWT);

    BasicResult<String> betting(BetBundle bundle);
}
