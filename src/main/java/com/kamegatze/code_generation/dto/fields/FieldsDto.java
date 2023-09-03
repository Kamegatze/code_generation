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

    public static List<FieldsDto> fromEntityToDto(List<Fields> fields) {
        return fields.stream()
                .map(item ->
                        FieldsDto.builder()
                                .id(item.getId())
                                .nameField(item.getNameField())
                                .nameType(item.getNameType())
                                .build()
                        )
                .toList();
    }
}