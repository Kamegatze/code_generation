package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import com.kamegatze.code_generation.dto.project.ProjectDto;
import com.kamegatze.code_generation.dto.response.EResponse;
import com.kamegatze.code_generation.dto.response.Response;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    private final RestOperations restOperations;
    @PostMapping("/create")
    public ResponseEntity<Response> handleCreateProject(@RequestBody ProjectConfigDTO config,
                                                        HttpServletRequest request) throws IOException {

        String url = projectService.getUrl(config);

        byte[] zip = restOperations.getForObject(url, byte[].class);

        String token = projectService.getJwt(request);

        projectService.extractFiles(zip, token, config.getBaseDirAndArtifactIdAndName());

        Response response = Response.builder()
                .message("Project " + config.getBaseDirAndArtifactIdAndName() + " was created")
                .code(EResponse.RESPONSE_CREATED.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/get_project/")
    public ResponseEntity<List<ProjectDto>> handleGetProject(HttpServletRequest httpServletRequest) {
        List<ProjectDto> projectDtos = projectService.getProjectsByUser(httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectDtos);
    }

}
