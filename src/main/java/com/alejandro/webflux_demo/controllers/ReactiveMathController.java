package com.alejandro.webflux_demo.controllers;

import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.dto.MultiplyRequest;
import com.alejandro.webflux_demo.services.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @PostMapping
    public Mono<MathResponse> multiply(@RequestBody Mono<MultiplyRequest> request) {
        return reactiveMathService.multiply(request);
    }
}
