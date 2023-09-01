package com.kamegatze.code_generation.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Table(name = "type",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fullName"}))
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  packageName;

    private String nameClass;

    private String fullName;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "type", cascade = CascadeType.REMOVE)
    private final List<Fields> fields = new ArrayList<>();
}
