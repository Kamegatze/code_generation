package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.entities.EntityCreateConfigDto;
import com.kamegatze.code_generation.dto.fields.CreateFieldDto;
import com.kamegatze.code_generation.dto.fields.FieldsDto;
import com.kamegatze.code_generation.entities.Fields;
import com.kamegatze.code_generation.entities.Type;
import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.repositories.FieldsRepository;
import com.kamegatze.code_generation.repositories.TypeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FieldsService {

    private final TypeRepository typeRepository;

    private final FieldsRepository fieldsRepository;

    private final EntityService entityService;

    @Transactional
    public void saveAll(Map<String, String> fields, Type type) {

        List<Fields> fieldsDB = new ArrayList<>();

        for (String nameField: fields.keySet()) {

            Fields field = Fields.builder()
                    .nameField(nameField)
                    .nameType(fields.get(nameField))
                    .type(type)
                    .build();

            fieldsDB.add(field);
        }

        fieldsRepository.saveAll(fieldsDB);

    }

    public List<FieldsDto> getFieldsByTypeId(Long typeId) {
        FieldsDto fieldsDto = new FieldsDto();

        return fieldsDto.changeFieldsToFieldsDto(
                typeRepository.findById(typeId)
                        .orElseThrow().getFields()
        );
    }

    @Transactional
    public Fields handleCreateField(CreateFieldDto createFieldDto, HttpServletRequest request) throws IOException, ClassNotFoundException {
        Type type = typeRepository.findById(createFieldDto.getEntityId()).orElseThrow();

        Fields field = Fields.builder()
                .type(type)
                .nameField(createFieldDto.getNameField())
                .nameType(createFieldDto.getNameType())
                .build();

        field = fieldsRepository.save(field);

        type.getFields().add(field);

        Map<String, String> fields = new HashMap<>();

        for (Fields fieldsClass : type.getFields()) {
            fields.put(fieldsClass.getNameField(), fieldsClass.getNameType());
        }

        EntityCreateConfigDto entityCreateConfigDto = EntityCreateConfigDto.builder()
                .fields(fields)
                .nameClass(type.getNameClass())
                .projectId(type.getProject().getId())
                .build();

        entityService.buildClass(entityCreateConfigDto, JwtUtils.getJwt(request));

        return field;
    }
}
