package com.alejandro.webflux_demo.util;

public class CalculatorValidationException extends RuntimeException {

    public CalculatorValidationException() {
        super("Only numbers permitted");
    }
}
