package com.alejandro.webflux_demo.util;

import lombok.Getter;

public class InputValidationException extends RuntimeException {

    private static final String MSG_EXCEPTION;
    public static final Integer ERROR_CODE;
    @Getter
    private final Integer input;

    static {
        MSG_EXCEPTION = "Only allowed range into 1 - 99";
        ERROR_CODE = 100;
    }

    public InputValidationException(Integer input) {
        super(MSG_EXCEPTION);
        this.input = input;
    }


}
