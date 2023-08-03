package com.kamegatze.code_generation.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nickname"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "You nickname can't equal empty")
    @Min(value = 2, message = "You nickname must be more 2 sign")
    private String nickname;

    @Email(message = "Please input correct email")
    @NotNull(message = "You email can't equal empty")
    private String email;

    @NotNull(message = "Please input your password")
    @Min(value = 8, message = "You password must be more 8 sign")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
