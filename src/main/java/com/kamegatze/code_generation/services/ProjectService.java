package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import com.kamegatze.code_generation.dto.project.ProjectDto;
import com.kamegatze.code_generation.entities.Project;
import com.kamegatze.code_generation.entities.User;
import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.repositories.ProjectRepository;
import com.kamegatze.code_generation.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

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

    private static final int BUFFER_SIZE = 4096;

    @Value("${application.unzip.file.path}")
    private String path;

    public String getUrl(ProjectConfigDTO config) {

        StringBuilder builderDependencies = new StringBuilder();
        for (int i = 0; i < config.getDependencies().size(); i++) {
            if(i == config.getDependencies().size() - 1) {
                builderDependencies.append(config.getDependencies().get(i));
            } else {
                builderDependencies.append(config.getDependencies().get(i)).append(",");
            }
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

    public String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if(!(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))) {
            return null;
        }

        return headerAuth.substring(7);
    }

    @Transactional(readOnly = false)
    public void extractFiles(byte[] zip, String token, String nameProject) throws IOException {

        String id = this.jwtUtils.getIdUser(token);

        unzip(zip, path + "/" + id);

        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow();

        Project project = Project.builder()
                .name(nameProject)
                .user(user)
                .build();

        user.getProjects().add(project);

        projectRepository.save(project);
    }

    private static void unzip(byte[] data, String dirName) throws IOException {
        File destDir = new File(dirName);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = dirName + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public List<ProjectDto> getProjectsByUser(Long id) {
        List<Project> projects = userRepository.findById(id)
                .orElseThrow().getProjects();

        ProjectDto projectDto = new ProjectDto();

        return projectDto.getProjects(projects);
    }
}
