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

    public static List<TypeDto> fromEntityToDto(List<Type> types) {
        return types.stream()
                .map(item ->
                        TypeDto.builder()
                                .id(item.getId())
                                .packageName(item.getPackageName())
                                .nameClass(item.getNameClass())
                                .fullName(item.getFullName())
                                .build()
                        )
                .toList();
    }
}
