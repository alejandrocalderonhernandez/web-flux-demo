package com.alejandro.webflux_demo.services;

import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ReactiveMathService {

    public Mono<MathResponse> findSquare(Integer input) {
        return Mono.fromSupplier(() -> MathResponse.builder()
                .date(LocalDateTime.now())
                .output(input * input)
                .build());
    }

    public Flux<MathResponse> multiplicationTable(Integer input) {
        return Flux.range(1, 10)
                .doOnNext(i -> Utils.sleepSeconds(1))
                .doOnNext(i -> log.info("Reactive service math :" + i))
                .map(i -> MathResponse.builder().date(LocalDateTime.now()).output(i * input).build());
    }
}
