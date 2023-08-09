package com.kamegatze.code_generation.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectConfigDTO {
    /*
    * gradle-project or maven-project
    * */
    private String type;
    /*
    * java or kotlin
    * */
    private String language;
    private String bootVersion;
    /*
    * name project
    * */
    private String baseDirAndArtifactIdAndName;
    /*
    * com.example
    * */
    private String groupId;
    private String description;
    /*
    * com.example.{name_project}
    * */
    private String packageName;
    /*
    * jar or war
    * */
    private String packaging;
    private String javaVersion;
    private List<String> dependencies;
}
