package com.kamegatze.code_generation.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityCreateConfigDto {

    private String nameClass;

    private Long projectId;
    /*
    * key this name variable, and value this type variable
    * */
    private Map<String, String> fields;
}
