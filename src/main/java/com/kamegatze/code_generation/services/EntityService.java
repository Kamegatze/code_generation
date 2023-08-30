package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.entities.EntityCreateConfigDto;
import com.kamegatze.code_generation.dto.entities.TypeDto;
import com.kamegatze.code_generation.entities.ETypeStandard;
import com.kamegatze.code_generation.entities.Project;
import com.kamegatze.code_generation.entities.Type;
import com.kamegatze.code_generation.entities.TypeStandard;
import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.logic_generate.GenerateClass;
import com.kamegatze.code_generation.logic_generate.GenerateEntity;
import com.kamegatze.code_generation.repositories.ProjectRepository;
import com.kamegatze.code_generation.repositories.TypeRepository;
import com.kamegatze.code_generation.repositories.TypeStandardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
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

    public String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if(!(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer "))) {
            return null;
        }

        return headerAuth.substring(7);
    }

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

        /*
        * Create generate entity
        * */
        GenerateClass entity = GenerateEntity.builder()
                .nameClass(config.getNameClass())
                .nameProject(config.getNameProject())
                .packageName(config.getPackageName())
                .path(this.path + "/" + userId)
                .fields(fields)
                .build();

        entity.toCreate();

        Project project = projectRepository.findById(config.getProjectId())
                .orElseThrow();

        /*
        * Create entity Type and addition in db
        * */
        Type type = Type.builder()
                .fullName(config.getPackageName() +  "."
                        + config.getNameProject() +
                        ".entity." +
                        config.getNameClass())
                .packageName(config.getPackageName() +  "."
                        + config.getNameProject() +
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

        TypeDto typeDto = new TypeDto();

        return typeDto.fromTypeListToTypeDtoList(project.getTypes());
    }

}
