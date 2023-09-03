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

    private String packageName;

    private String fullPackageName;

    private String type;

    private String bootVersion;

    public static List<ProjectDto> fromEntityToDto(List<Project> projects) {
        return projects.stream()
                .map(item ->
                        ProjectDto.builder()
                                .id(item.getId())
                                .name(item.getName())
                                .packageName(item.getPackageName())
                                .fullPackageName(item.getFullPackageName())
                                .type(item.getType())
                                .bootVersion(item.getBootVersion())
                                .build()
                        )
                .toList();
    }

}
