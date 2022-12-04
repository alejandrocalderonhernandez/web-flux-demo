package com.alejandro.webflux_demo.controllers;

import com.alejandro.webflux_demo.dto.MathResponse;
import com.alejandro.webflux_demo.services.MathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "math")
public class MathController {

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping(path = "square/{input}")
    public ResponseEntity<MathResponse> getSquare(@PathVariable Integer input) {
        return ResponseEntity.ok(mathService.findSquare(input));
    }

    @GetMapping(path = "multiplication/{input}")
    public ResponseEntity<List<MathResponse>> getMultiplicationTable(@PathVariable Integer input) {
        return ResponseEntity.ok(mathService.multiplicationTable(input));
    }

}
