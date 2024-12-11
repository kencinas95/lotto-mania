package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.models.UserSession;
import ar.com.kaijusoftware.lotto_mania.repositories.UserSessionRepository;
import ar.com.kaijusoftware.lotto_mania.services.AuthService;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserSessionRepository userSessionRepository;
    @Override
    public String createAuthToken(final String signature, final Map<String, ?> claims) {
        log.debug("Creating a new signed JWT ({}) with claims: {}", signature, claims);
        return Jwts.builder()
                .claims(claims)
                .signWith(createKeyFromSignature(signature))
                .compact();
    }

    @Override
    public BasicResult<Boolean> isValidSession(String sid) {
        return BasicResult.ok(false);
    }

    private static Key createKeyFromSignature(final String signature) {
        return Keys.hmacShaKeyFor(signature.getBytes());
    }
}
