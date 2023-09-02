package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.entities.EntityCreateConfigDto;
import com.kamegatze.code_generation.dto.entities.TypeDto;
import com.kamegatze.code_generation.dto.response.EResponse;
import com.kamegatze.code_generation.dto.response.Response;
import com.kamegatze.code_generation.entities.Type;
import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.services.EntityService;
import com.kamegatze.code_generation.services.FieldsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entity")
@RequiredArgsConstructor
public class EntityController {

    private final EntityService entityService;

    private final FieldsService fieldsService;

    @PostMapping("/create_entity")
    public ResponseEntity<Response> handleCreateEntity(
            @RequestBody EntityCreateConfigDto config,
            UriComponentsBuilder uri,
            HttpServletRequest httpServletRequest
    ) throws IOException, ClassNotFoundException {

        String token = JwtUtils.getJwt(httpServletRequest);

        Type type = entityService.buildClass(config, token);

        fieldsService.saveAll(config.getFields(), type);

        Response response = Response.builder()
                .message("Entity " + config.getNameClass() + " was created")
                .code(EResponse.RESPONSE_CREATED.getCode())
                .build();

        return ResponseEntity.created(uri.path("/api/entity/{id}").build(Map.of("id", type.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/remove/{entityId}")
    public ResponseEntity<Response> handlerRemoveEntityById(@PathVariable Long entityId, HttpServletRequest httpServletRequest) throws IOException {
        entityService.removeById(entityId, httpServletRequest);

        Response response = Response.builder()
                .message("Entity with id: " + entityId + "was deleted")
                .code(EResponse.RESPONSE_DELETED.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDto> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(entityService.getById(id));
    }

    @GetMapping("/get_types_by_project_id/{project_id}")
    public ResponseEntity<List<TypeDto>> handleTypeByProjectId(@PathVariable Long project_id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(entityService.handleTypeByProjectId(project_id));
    }
}
