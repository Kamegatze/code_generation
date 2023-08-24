package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.project.EntityConfigDto;
import com.kamegatze.code_generation.entities.Type;
import com.kamegatze.code_generation.logic_generate.GenerateClass;
import com.kamegatze.code_generation.logic_generate.GenerateEntity;
import com.kamegatze.code_generation.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EntityService {

    @Value("${application.unzip.file.path}")
    private String path;

    private final TypeRepository typeRepository;

    @Transactional
    public void buildClass(EntityConfigDto config) throws IOException, ClassNotFoundException {

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

        this.typeRepository.save(type);



        GenerateClass entity = GenerateEntity.builder()
                .nameClass(config.getNameClass())
                .nameProject(config.getNameProject())
                .packageName(config.getPackageName())
                .path(this.path)
                .fields(config.getFields())
                .build();

        entity.toCreate();
    }

}
