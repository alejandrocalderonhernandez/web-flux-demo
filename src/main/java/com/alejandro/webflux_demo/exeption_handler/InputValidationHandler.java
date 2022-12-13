package com.alejandro.webflux_demo.exeption_handler;

import com.alejandro.webflux_demo.dto.InputFailedValidationResponse;
import com.alejandro.webflux_demo.util.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleInputValidationException(InputValidationException e) {
            var response = InputFailedValidationResponse.builder()
                    .input(e.getInput())
                    .message(e.getMessage())
                    .code(InputValidationException.ERROR_CODE)
                    .build();
            return ResponseEntity.badRequest().body(response);
    }
}
