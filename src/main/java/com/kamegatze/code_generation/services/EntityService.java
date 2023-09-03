package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.entities.EntityCreateConfigDto;
import com.kamegatze.code_generation.dto.entities.TypeDto;
import com.kamegatze.code_generation.entities.*;
import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.logic_generate.GenerateClass;
import com.kamegatze.code_generation.logic_generate.GenerateEntity;
import com.kamegatze.code_generation.repositories.FieldsRepository;
import com.kamegatze.code_generation.repositories.ProjectRepository;
import com.kamegatze.code_generation.repositories.TypeRepository;
import com.kamegatze.code_generation.repositories.TypeStandardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EntityService {

    @Value("${application.unzip.file.path}")
    private String path;

    private final TypeRepository typeRepository;

    private final TypeStandardRepository typeStandardRepository;

    private final ProjectRepository projectRepository;

    private final JwtUtils jwtUtils;
    @Transactional
    public Type buildClass(EntityCreateConfigDto config, String token) throws IOException, ClassNotFoundException {

        Map<String, String> fields = new HashMap<>();
        /*
        * Parse type from String to java.lang.String and etc;
        * */

        String userId = jwtUtils.getIdUser(token);

        for (String key : config.getFields().keySet()) {
            TypeStandard typeStandard = typeStandardRepository
                    .findByNameClass(
                            ETypeStandard.valueOf(
                                    config.getFields().get(key)
                            )
                    )
                    .orElseThrow();
            fields.put(key, typeStandard.getFullName());
        }

        Project project = projectRepository.findById(config.getProjectId())
                .orElseThrow();

        /*
        * Create generate entity
        * */
        GenerateClass entity = GenerateEntity.builder()
                .nameClass(config.getNameClass())
                .nameProject(project.getName())
                .packageName(project.getPackageName())
                .path(this.path + "/" + userId)
                .fields(fields)
                .build();

        entity.toCreate();

        /*
        * Create entity Type and addition in db
        * */
        Type type = Type.builder()
                .fullName(project.getFullPackageName() +
                        ".entity." +
                        config.getNameClass())
                .packageName(project.getFullPackageName() +
                        ".entity")
                .nameClass(config.getNameClass())
                .project(project)
                .build();

        if(typeRepository.existsByFullName(type.getFullName())) {
            return this.typeRepository.findByFullName(type.getFullName()).orElseThrow();
        }
        return this.typeRepository.save(type);
    }

    public TypeDto getById(Long id) {
        Type type = typeRepository.findById(id)
                .orElseThrow();

        return TypeDto.builder()
                .id(type.getId())
                .fullName(type.getFullName())
                .packageName(type.getPackageName())
                .nameClass(type.getNameClass())
                .build();
    }

    public List<TypeDto> handleTypeByProjectId(Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow();

        return TypeDto.fromEntityToDto(project.getTypes());
    }

    private void deleteFromFolder(HttpServletRequest httpServletRequest, Long entityId) throws IOException {

        String id = jwtUtils.getIdUser(JwtUtils.getJwt(httpServletRequest));

        Type type = typeRepository.findById(entityId).orElseThrow();

        String pathPackage = type.getFullName().replace('.', '/') + ".java";

        String path = this.path + "/" + id + "/" + type.getProject().getName() + "/src/main/java/" + pathPackage;

        System.out.println(path);

        Files.deleteIfExists(Paths.get(path));
    }

    @Transactional
    public void removeById(Long entityId, HttpServletRequest httpServletRequest) throws IOException {
        deleteFromFolder(httpServletRequest, entityId);
        typeRepository.deleteById(entityId);
    }
}
