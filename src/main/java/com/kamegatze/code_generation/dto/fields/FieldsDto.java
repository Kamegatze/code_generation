package com.kamegatze.code_generation.dto.fields;

import com.kamegatze.code_generation.entities.Fields;
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
public class FieldsDto {
    private Long id;

    private String nameField;

    private String nameType;

    public List<FieldsDto> changeFieldsToFieldsDto(List<Fields> fields) {
        List<FieldsDto> fieldsDtos = new ArrayList<>();

        for (Fields field : fields) {
            FieldsDto fieldsDto = FieldsDto.builder()
                    .id(field.getId())
                    .nameField(field.getNameField())
                    .nameType(field.getNameType())
                    .build();

            fieldsDtos.add(fieldsDto);
        }

        return fieldsDtos;
    }
}