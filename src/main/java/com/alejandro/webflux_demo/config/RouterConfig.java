package com.alejandro.webflux_demo.config;

import com.alejandro.webflux_demo.dto.InputFailedValidationResponse;
import com.alejandro.webflux_demo.services.MathHandler;
import com.alejandro.webflux_demo.util.CalculatorValidationException;
import com.alejandro.webflux_demo.util.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    private final MathHandler mathHandler;

    public static final String header_operation_type = "OPERATION_TYPE";

    public RouterConfig(MathHandler mathHandler) {
        this.mathHandler = mathHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> handlerMath() {
        return RouterFunctions
                .route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions
                .route()
                .GET("square/{input}", mathHandler::squareHandler)
                .GET("table/{input}", mathHandler::multiplyHandler)
                .GET("table/stream/{input}", mathHandler::multiplyStreamHandler)
                .GET("calculator/{a}/{b}", mathHandler::calculatorTwoValues)
                .POST("table", mathHandler::multiplyPostHandler)
                .onError(InputValidationException.class, exception_handler)
                .onError(CalculatorValidationException.class, exception_calculator_handler)
                .build();
    }

    private static final BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exception_handler = (err, req) -> {
        var ex = (InputValidationException) err;
        var errorResponse = InputFailedValidationResponse.builder()
                .input(ex.getInput())
                .message(ex.getMessage())
                .code(InputValidationException.ERROR_CODE)
                .build();
        return ServerResponse.badRequest().bodyValue(errorResponse);
    };

    private static final BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exception_calculator_handler = (err, req) -> {
        var ex = (CalculatorValidationException) err;
        return ServerResponse.badRequest().bodyValue(ex.getMessage());
    };
}
