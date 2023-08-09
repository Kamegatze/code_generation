package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final String url = "https://start.spring.io/starter.zip?" +
            "type=%s&" +
            "language=%s&" +
            "bootVersion=%s&" +
            "baseDir=%s&" +
            "groupId=%s&" +
            "artifactId=%s&" +
            "name=%s&" +
            "description=%s&" +
            "packageName=%s&" +
            "packaging=%s&" +
            "javaVersion=%s&" +
            "dependencies=%s";


    public String getUrl(ProjectConfigDTO config) {

        StringBuilder builderDependencies = new StringBuilder();
        for (int i = 0; i < config.getDependencies().size(); i++) {
            if(i == config.getDependencies().size() - 1) {
                builderDependencies.append(config.getDependencies().get(i));
            }

            builderDependencies.append(config.getDependencies().get(i)).append(",");
        }

        String dependencies = builderDependencies.toString();

         return String.format(url,
                config.getType(),
                config.getLanguage(),
                config.getBootVersion(),
                config.getBaseDirAndArtifactIdAndName(),
                config.getGroupId(),
                config.getBaseDirAndArtifactIdAndName(),
                config.getBaseDirAndArtifactIdAndName(),
                config.getDescription(),
                config.getPackageName(),
                config.getPackaging(),
                config.getJavaVersion(),
                dependencies
         );
    }
}
