package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.entities.EntityCreateConfigDto;
import com.kamegatze.code_generation.dto.response.EResponse;
import com.kamegatze.code_generation.dto.response.Response;
import com.kamegatze.code_generation.services.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/entity")
@RequiredArgsConstructor
public class EntityController {

    private final EntityService entityService;
    
    @PostMapping("/create_entity")
    public ResponseEntity<Response> handleCreateEntity(@RequestBody EntityCreateConfigDto config) throws IOException, ClassNotFoundException {

        entityService.buildClass(config);

        Response response = Response.builder()
                .message("Entity " + config.getNameClass() + " was created")
                .code(EResponse.RESPONSE_CREATED.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    
}
