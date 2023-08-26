package com.kamegatze.code_generation.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "project",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "name"
        )
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "project")
    private List<Type> types;
}
