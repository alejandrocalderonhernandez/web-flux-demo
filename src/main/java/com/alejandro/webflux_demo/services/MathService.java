package com.alejandro.webflux_demo.services;

import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class MathService {

    public MathResponse findSquare(Integer input) {
        return MathResponse.builder()
                .date(LocalDateTime.now())
                .output(input * input)
                .build();
    }

    public List<MathResponse> multiplicationTable(Integer input) {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> Utils.sleepSeconds(1))
                .peek(i -> log.info("Service math :" + i))
                .mapToObj(i -> MathResponse.builder().date(LocalDateTime.now()).output(i * input).build())
                .collect(Collectors.toList());
    }
}
