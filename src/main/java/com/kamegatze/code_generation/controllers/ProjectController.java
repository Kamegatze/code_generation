package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import com.kamegatze.code_generation.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    private final RestOperations restOperations;
    @PostMapping("/create")
    public ResponseEntity<?> handleCreateProject(@RequestBody ProjectConfigDTO config) {

        String url = projectService.getUrl(config);

        byte[] zip = restOperations.getForObject(url, byte[].class);

        return ResponseEntity.ok().build();
    }

}
