package com.kamegatze.code_generation.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityConfigDto {
    private String nameClass;

    private String nameProject;

    private String packageName;
    /*
    * key this name variable, and value this type variable
    * */
    private Map<String, String> fields;
}
