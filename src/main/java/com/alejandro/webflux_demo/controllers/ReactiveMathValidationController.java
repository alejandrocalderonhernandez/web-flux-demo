package com.alejandro.webflux_demo.controllers;

import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.services.ReactiveMathService;
import com.alejandro.webflux_demo.util.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "reactive-math")
public class ReactiveMathValidationController {

    private final ReactiveMathService reactiveMathService;

    public ReactiveMathValidationController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping(path = "square/{input}/throw")
    public Mono<MathResponse> getSquare(@PathVariable Integer input) {
        if (input > 99 || input < 1) throw new InputValidationException(input);
        return reactiveMathService.findSquare(input);
    }

    @GetMapping(path = "square/{input}/mono-error")
    public Mono<MathResponse> getSquareReactive(@PathVariable Integer input) {
        return Mono
                .just(input)
                .handle((i, synk) -> {
                    if (i > 99) {
                        synk.error(new InputValidationException(i));
                    } else {
                        synk.next(i);
                    }
                })
                .cast(Integer.class)
                .flatMap(reactiveMathService::findSquare);
    }

    @GetMapping(path = "square/{input}/assigment")
    public Mono<ResponseEntity<MathResponse>> getSquareAssigment(@PathVariable Integer input) {
        return Mono
                .just(input)
                .filter(i -> i < 99)
                .flatMap(reactiveMathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


}
