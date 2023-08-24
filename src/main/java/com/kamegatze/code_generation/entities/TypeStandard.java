package com.kamegatze.code_generation.entities;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TypeStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  packageName;

    @Enumerated(EnumType.STRING)
    private ETypeStandard nameClass;

    private String fullName;
}
