package com.alejandro.webflux_demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputFailedValidationResponse {

    private Integer code;
    private Integer input;
    private String message;
}
