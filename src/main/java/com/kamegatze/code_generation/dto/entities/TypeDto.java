package com.kamegatze.code_generation.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {
    private Long id;

    private String  packageName;

    private String nameClass;

    private String fullName;
}
