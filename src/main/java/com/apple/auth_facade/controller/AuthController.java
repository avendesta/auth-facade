package com.apple.auth_facade.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.apple.auth_facade.dto.AuthorizationRequest;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;
import java.time.Duration;


@RestController
public class AuthController {

    private final WebClient webClient;

    @Value("${external.endpoint.url}")
    private String externalEndpointUrl;

    @Value("${external.endpoint.timeout}")
    private int externalEndpointTimeout; // Timeout in seconds

    public AuthController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

@PostMapping(value = "/authorize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Mono<Map<String, Boolean>> checkExternalEndpoint(@Valid @RequestBody AuthorizationRequest request) {
    return webClient.post()
        .uri(externalEndpointUrl)
        .bodyValue(request) // Send the request body to the external endpoint
        .retrieve()
        .bodyToMono(Boolean.class)
        .timeout(Duration.ofSeconds(externalEndpointTimeout)) // timeout after 5 seconds
        .map(auth -> Map.of("auth", auth))
        .onErrorResume(error -> {
            if (error instanceof java.util.concurrent.TimeoutException) {
                System.err.println("Timeout occurred while calling external endpoint.");
            } else {
                System.err.println("Error occurred while calling external endpoint: " + error.getMessage());
            }
            // Return a fallback response
            return Mono.just(Map.of("auth", false));
        });
}
}