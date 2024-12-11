package ar.com.kaijusoftware.lotto_mania.services.impl;

import ar.com.kaijusoftware.lotto_mania.models.MainframeProduct;
import ar.com.kaijusoftware.lotto_mania.repositories.MainframeProductRepository;
import ar.com.kaijusoftware.lotto_mania.services.AuthService;
import ar.com.kaijusoftware.lotto_mania.services.MainframeProductService;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainframeProductServiceImpl implements MainframeProductService {

    private final MainframeProductRepository mainframeProductRepository;

    private final AuthService authService;


    @Override
    public Boolean isProductActivated() {
        log.debug("Checking if the product is activated...");
        return mainframeProductRepository.findById("product.activated")
                .map(MainframeProduct::<Boolean>get)
                .orElseThrow();
    }

    @Override
    public BasicResult<Boolean> activate(final String key, final String username, final String password) {
        log.debug("Activating product with key ({}) for user: {}({})", key, username, password);

        final MainframeProduct isActivated = mainframeProductRepository.findById("product.activated").orElseThrow();
        if (isActivated.<Boolean>get()) {
            return BasicResult.error("Product is already activated");
        }

        // Activation URL
        final URI activationURL = mainframeProductRepository.findById("activation.url")
                .map(MainframeProduct::<String>get)
                .map(URI::create)
                .orElseThrow();

        // Get the product code from the db
        final String code = mainframeProductRepository.findById("product.code")
                .map(MainframeProduct::<String>get)
                .orElseThrow();

        // Get the product signature from the db
        final String signature = mainframeProductRepository.findById("product.signature")
                .map(MainframeProduct::<String>get)
                .orElseThrow();

        // Claims for the JWT
        final Map<String, String> claims = Map.of(
                "sub", username,
                "pwd", password,
                "iss", code,
                "key", key
        );

        // JWT data
        final String data = authService.createAuthToken(signature, claims);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(activationURL)
                .header("LP_ACTIVATION_REQUEST", data)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {

                isActivated.setValue("true");
                mainframeProductRepository.save(isActivated);

                return BasicResult.ok(true);
            } else {
                return BasicResult.error(response.body());
            }
        } catch (IOException | InterruptedException ex) {
            return BasicResult.error("Unable to activate product: " + ex.getMessage(), ex);
        }
    }

    private void createRootUser(final String username, final String password){

    }

}
