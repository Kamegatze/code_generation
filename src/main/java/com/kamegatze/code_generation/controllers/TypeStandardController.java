package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.type_standart.TypeStandardDto;
import com.kamegatze.code_generation.services.TypeStandardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/type_standard")
@RequiredArgsConstructor
public class TypeStandardController {

    private final TypeStandardService typeStandardService;

    @GetMapping("/")
    public ResponseEntity<List<TypeStandardDto>> handleGetTypeStandard() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(typeStandardService.handleGetTypeStandard());
    }
}
