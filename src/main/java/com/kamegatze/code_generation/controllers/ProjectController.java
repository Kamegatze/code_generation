package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.auth.JwtDto;
import com.kamegatze.code_generation.dto.project.EntityConfigDto;
import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import com.kamegatze.code_generation.services.EntityService;
import com.kamegatze.code_generation.services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    private final EntityService entityService;

    private final RestOperations restOperations;
    @PostMapping("/create")
    public ResponseEntity<?> handleCreateProject(@RequestBody ProjectConfigDTO config,
                                                 HttpServletRequest request) throws IOException {

        String url = projectService.getUrl(config);

        byte[] zip = restOperations.getForObject(url, byte[].class);

        String token = projectService.getJwt(request);

        projectService.extractFiles(zip, token, config.getBaseDirAndArtifactIdAndName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Project created!!!"));
    }

    @PostMapping("/create_entity")
    public ResponseEntity<?> handleCreateEntity(@RequestBody EntityConfigDto config) throws IOException, ClassNotFoundException {

        entityService.buildClass(config);

        return ResponseEntity.ok().build();
    }

}
