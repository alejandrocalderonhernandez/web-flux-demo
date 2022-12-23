package com.alejandro.webflux_demo.controller;

import com.alejandro.webflux_demo.dto.MathResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class ReactiveMathControllerTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        var response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(MathResponse.class)
                .block();

        assert response != null;
        Assertions.assertEquals(25, response.getOutput());

    }

    @Test
    public void stepVerifierTest() {
        var response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(MathResponse.class);

        StepVerifier
                .create(response)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();

    }
}
