package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.fields.FieldsDto;
import com.kamegatze.code_generation.services.FieldsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldsController {

    private final FieldsService fieldsService;

    @GetMapping("/{typeId}")
    public ResponseEntity<List<FieldsDto>> getFieldsByEntity(@PathVariable Long typeId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fieldsService.getFieldsByTypeId(typeId));
    }
}
