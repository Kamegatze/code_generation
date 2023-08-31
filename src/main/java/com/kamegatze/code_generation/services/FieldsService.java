package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.fields.FieldsDto;
import com.kamegatze.code_generation.entities.Fields;
import com.kamegatze.code_generation.entities.Type;
import com.kamegatze.code_generation.repositories.FieldsRepository;
import com.kamegatze.code_generation.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FieldsService {

    private final TypeRepository typeRepository;

    private final FieldsRepository fieldsRepository;

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

}
