package com.kamegatze.code_generation.entities;

import jakarta.persistence.*;
import lombok.*;


@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameField;

    private String nameType;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;
}
