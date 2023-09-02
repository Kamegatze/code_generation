package com.kamegatze.code_generation.dto.fields;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFieldDto {

    private String  nameType;

    private String nameField;

    private Long entityId;
}