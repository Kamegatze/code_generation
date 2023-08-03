package com.kamegatze.code_generation.dto.auth;


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
public class SignInDto extends RepresentationModel<SignInDto> {

    /*
    * login is nickname and email
    * */
    @NotNull(message = "You login can't equal empty")
    @Size(min = 2, message = "You login must be more 2 sign")
    @NotBlank(message = "The login field should not be empty")
    private String login;

    @NotNull(message = "Please input your password")
    @Size(min = 8, message = "You password must be more 8 sign")
    @NotBlank(message = "The password field should not be empty")
    private String password;
}
