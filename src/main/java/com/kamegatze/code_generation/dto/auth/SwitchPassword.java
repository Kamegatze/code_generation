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
public class SwitchPassword {

    @NotNull
    @NotBlank(message = "Your login not need must be empty")
    @Size(min = 2, message = "Your login need must be more 2 sign")
    private String login;

}
