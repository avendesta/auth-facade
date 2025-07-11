package com.apple.auth_facade.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private final WebClient webClient;

    @Value("${external.endpoint.url}")
    private String externalEndpointUrl;

    public AuthController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping(value = "/authorize", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Boolean>> checkExternalEndpoint() {
        return webClient.get()
                .uri(externalEndpointUrl)
                .retrieve()
                .bodyToMono(Boolean.class)
                .map(auth -> Map.of("auth", auth));
    }
}