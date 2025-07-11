package com.apple.auth_facade.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.apple.auth_facade.dto.AuthorizationRequest;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private final WebClient webClient;

    @Value("${external.endpoint.url}")
    private String externalEndpointUrl;

    public AuthController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

@PostMapping(value = "/authorize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public Mono<Map<String, Boolean>> checkExternalEndpoint(@RequestBody AuthorizationRequest request) {
    return webClient.post()
        .uri(externalEndpointUrl)
        .bodyValue(request) // Send the request body to the external endpoint
        .retrieve()
        .bodyToMono(Boolean.class)
        .map(auth -> Map.of("auth", auth))
        .onErrorResume(error -> {
            // Log the error (optional)
            System.err.println("Error occurred while calling external endpoint: " + error.getMessage());
            // Return a fallback response
            return Mono.just(Map.of("auth", false));
        });
}
}