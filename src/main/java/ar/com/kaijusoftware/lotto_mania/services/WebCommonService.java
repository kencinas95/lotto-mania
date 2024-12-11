package ar.com.kaijusoftware.lotto_mania.services;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WebCommonService {
    public <T> ResponseEntity<T> redirects(String url, Optional<HttpStatus> status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", url);
        return new ResponseEntity<>(headers, status.orElse(HttpStatus.FOUND));
    }
}
