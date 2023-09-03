package com.kamegatze.code_generation.dto.type_standart;

import com.kamegatze.code_generation.entities.ETypeStandard;
import com.kamegatze.code_generation.entities.TypeStandard;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeStandardDto {
    private Long id;

    private String  packageName;

    private ETypeStandard nameClass;

    private String fullName;
    public static List<TypeStandardDto> fromEntityToDto(List<TypeStandard> all) {
        return all.stream().map(item ->
            TypeStandardDto.builder()
                    .id(item.getId())
                    .packageName(item.getPackageName())
                    .nameClass(item.getNameClass())
                    .fullName(item.getFullName())
                    .build()
        ).toList();
    }
}
