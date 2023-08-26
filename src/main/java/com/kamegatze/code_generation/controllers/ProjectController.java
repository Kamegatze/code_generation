package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.project.EntityCreateConfigDto;
import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import com.kamegatze.code_generation.dto.project.ProjectDto;
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
import java.util.List;
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

    @GetMapping("/get_project/{user_id}")
    public ResponseEntity<List<ProjectDto>> handleGetProject(@PathVariable Long user_id) {
        List<ProjectDto> projectDtos = projectService.getProjectsByUser(user_id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectDtos);
    }

    @PostMapping("/create_entity")
    public ResponseEntity<?> handleCreateEntity(@RequestBody EntityCreateConfigDto config) throws IOException, ClassNotFoundException {

        entityService.buildClass(config);

        return ResponseEntity.ok().build();
    }

}
