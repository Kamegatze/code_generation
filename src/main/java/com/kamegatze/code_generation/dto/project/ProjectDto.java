package com.kamegatze.code_generation.dto.project;

import com.kamegatze.code_generation.dto.entities.TypeDto;
import com.kamegatze.code_generation.entities.Project;
import com.kamegatze.code_generation.entities.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private Long id;

    private String name;

    public List<ProjectDto> getProjects(List<Project> projects) {
        List<ProjectDto> projectDtos = new ArrayList<>();

        for (Project project : projects) {

            projectDtos.add(
                    ProjectDto.builder()
                            .id(project.getId())
                            .name(project.getName())
                            .build()
            );
        }

        return projectDtos;
    }
}