package com.kamegatze.code_generation.dto.auth;


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
    private String login;

    private String password;
}
