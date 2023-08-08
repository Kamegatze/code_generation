package com.kamegatze.code_generation.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
    @NotNull(message = "Please input your password")
    @Size(min = 8, message = "You password must be more 8 sign")
    @NotBlank(message = "The password field should not be empty")
    private String newPassword;

    @NotNull(message = "Please input your password")
    @Size(min = 8, message = "You password must be more 8 sign")
    @NotBlank(message = "The password field should not be empty")
    private String retryPassword;

    @Size( min = 7, max = 7, message = "Code must be 7 sign")
    @NotNull(message = "Code not must be equals null")
    @NotBlank(message = "Code not must be equals empty")
    private String code;
}
