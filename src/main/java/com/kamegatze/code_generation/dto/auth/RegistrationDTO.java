package com.kamegatze.code_generation.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegistrationDTO extends RepresentationModel<RegistrationDTO> {

    @NotNull(message = "You nickname can't equal empty")
    @Size(min = 2, message = "You nickname must be more 2 sign")
    @NotBlank(message = "The nickname field should not be empty")
    private String nickname;

    @NotNull(message = "Please input your password")
    @Size(min = 8, message = "You password must be more 8 sign")
    @NotBlank(message = "The password field should not be empty")
    private String password;

    @Email(message = "Please input correct email")
    @NotNull(message = "You email can't equal empty")
    @NotBlank(message = "The email field should not be empty")
    private String email;

    private String role;
}
