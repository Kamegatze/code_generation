package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.fields.CreateFieldDto;
import com.kamegatze.code_generation.dto.fields.FieldsDto;
import com.kamegatze.code_generation.dto.response.EResponse;
import com.kamegatze.code_generation.dto.response.Response;
import com.kamegatze.code_generation.entities.Fields;
import com.kamegatze.code_generation.services.FieldsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/create")
    public ResponseEntity<Response> handleCreateField(
            @RequestBody CreateFieldDto createFieldDto,
            UriComponentsBuilder uri,
            HttpServletRequest request) throws IOException, ClassNotFoundException {

        Fields field = fieldsService.handleCreateField(createFieldDto, request);

        Response response = Response.builder()
                .message("Field " + createFieldDto.getNameField() + " was created!")
                .code(EResponse.RESPONSE_CREATED.getCode())
                .build();

        return ResponseEntity.created(
                uri.path("/api/fields/field/{fieldId}")
                        .build(Map.of("fieldId", field.getId()))
        )
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
