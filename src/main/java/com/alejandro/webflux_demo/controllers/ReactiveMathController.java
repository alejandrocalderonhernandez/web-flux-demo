package com.alejandro.webflux_demo.controllers;

import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.services.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path = "reactive-math")
public class ReactiveMathController {

    private final ReactiveMathService reactiveMathService;

    public ReactiveMathController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping(path = "square/{input}")
    public Mono<MathResponse> getSquare(@PathVariable Integer input) {
        return reactiveMathService.findSquare(input);
    }

    @GetMapping(path = "multiplication/{input}")
    public Flux <MathResponse> getMultiplicationTable(@PathVariable Integer input) {
        return reactiveMathService.multiplicationTable(input);
    }

    @GetMapping(path = "multiplication/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux <MathResponse> getMultiplicationTableStream(@PathVariable Integer input) {
        return reactiveMathService.multiplicationTable(input);
    }
}
