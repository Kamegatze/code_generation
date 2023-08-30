package com.kamegatze.code_generation.dto.entities;

import com.kamegatze.code_generation.entities.Type;
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
public class TypeDto {
    private Long id;

    private String  packageName;

    private String nameClass;

    private String fullName;

    public List<TypeDto> fromTypeListToTypeDtoList(List<Type> types) {
        List<TypeDto> typeDtos = new ArrayList<>();

        for (Type type : types) {
            TypeDto typeDto = TypeDto.builder()
                    .id(type.getId())
                    .fullName(type.getFullName())
                    .nameClass(type.getNameClass())
                    .packageName(type.getPackageName())
                    .build();

            typeDtos.add(typeDto);
        }

        return typeDtos;
    }
}
