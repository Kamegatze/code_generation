package com.kamegatze.code_generation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CustomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;

    private String className;
}
