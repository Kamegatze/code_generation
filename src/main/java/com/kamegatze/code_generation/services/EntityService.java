package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.project.EntityConfigDto;
import com.kamegatze.code_generation.entities.ETypeStandard;
import com.kamegatze.code_generation.entities.Type;
import com.kamegatze.code_generation.entities.TypeStandard;
import com.kamegatze.code_generation.logic_generate.GenerateClass;
import com.kamegatze.code_generation.logic_generate.GenerateEntity;
import com.kamegatze.code_generation.repositories.TypeRepository;
import com.kamegatze.code_generation.repositories.TypeStandardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void buildClass(EntityConfigDto config) throws IOException, ClassNotFoundException {

        Map<String, String> fields = new HashMap<>();

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

        GenerateClass entity = GenerateEntity.builder()
                .nameClass(config.getNameClass())
                .nameProject(config.getNameProject())
                .packageName(config.getPackageName())
                .path(this.path)
                .fields(fields)
                .build();

        entity.toCreate();

        Type type = Type.builder()
                .fullName(config.getPackageName() +  "."
                        + config.getNameProject() +
                        ".entity." +
                        config.getNameClass())
                .packageName(config.getPackageName() +  "."
                        + config.getNameProject() +
                        ".entity")
                .nameClass(config.getNameClass())
                .build();

        if(!typeRepository.existsByFullName(type.getFullName())) {
            this.typeRepository.save(type);
        }

    }

}
