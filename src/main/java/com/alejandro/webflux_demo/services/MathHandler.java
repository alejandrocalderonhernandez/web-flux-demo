package com.alejandro.webflux_demo.services;

import com.alejandro.webflux_demo.config.RouterConfig;
import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.dto.MultiplyRequest;
import com.alejandro.webflux_demo.util.CalculatorValidationException;
import com.alejandro.webflux_demo.util.InputValidationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MathHandler {

    private final ReactiveMathService reactiveMathService;

    public MathHandler(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        var input = Integer.valueOf(request.pathVariable("input"));
        if (input > 99) {
            return Mono.error(new InputValidationException(input));
        }
        var serviceResponse = reactiveMathService.findSquare(input);
        return ServerResponse.ok().body(serviceResponse, MathResponse.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        var input = Integer.valueOf(request.pathVariable("input"));
        var serviceResponse = reactiveMathService.multiplicationTable(input);
        return ServerResponse.ok().body(serviceResponse, MathResponse.class);
    }

    public Mono<ServerResponse> multiplyStreamHandler(ServerRequest request) {
        var input = Integer.valueOf(request.pathVariable("input"));
        var serviceResponse = reactiveMathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(serviceResponse, MathResponse.class);
    }

    public Mono<ServerResponse> multiplyPostHandler(ServerRequest request) {
        var body = request.bodyToMono(MultiplyRequest.class);
        var serviceResponse = reactiveMathService.multiply(body);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(serviceResponse, MathResponse.class);
    }

    public Mono<ServerResponse> calculatorTwoValues(ServerRequest request) {
        try {
            var a = Integer.parseInt(request.pathVariable("a"));
            var b = Integer.parseInt(request.pathVariable("b"));
            var operationType = request.headers().header(RouterConfig.header_operation_type);
            var serviceResponse = reactiveMathService.calculator(a, b, operationType.get(0));
            return ServerResponse
                    .ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(serviceResponse, Double.class);
        } catch (NumberFormatException nfe) {
            return Mono.error(new CalculatorValidationException());
        }

    }
}
