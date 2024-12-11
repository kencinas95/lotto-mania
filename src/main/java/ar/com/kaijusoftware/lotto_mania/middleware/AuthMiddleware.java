package ar.com.kaijusoftware.lotto_mania.middleware;

import java.io.IOException;

import org.springframework.stereotype.Component;

import ar.com.kaijusoftware.lotto_mania.services.impl.UserServiceImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthMiddleware implements Filter {

    private final UserServiceImpl userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        String rawSessionJWT = httpRequest.getHeader("USID");
        {
        }
    }

}
