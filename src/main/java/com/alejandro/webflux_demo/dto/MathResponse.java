package com.alejandro.webflux_demo.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MathResponse {

    private LocalDateTime date;
    private Integer output;

}
