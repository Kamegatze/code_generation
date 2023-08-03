package com.kamegatze.code_generation.dto.auth;

import com.kamegatze.code_generation.entities.ERole;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegistrationDTO extends RepresentationModel<RegistrationDTO> {

    private String nickname;

    private String password;

    private String email;

    private String role;
}
