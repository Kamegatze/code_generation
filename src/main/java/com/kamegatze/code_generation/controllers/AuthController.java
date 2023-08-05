package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.custom_exception.EmailExistException;
import com.kamegatze.code_generation.custom_exception.NicknameExistException;
import com.kamegatze.code_generation.dto.auth.JwtDto;
import com.kamegatze.code_generation.dto.auth.RegistrationDTO;
import com.kamegatze.code_generation.dto.auth.SignInDto;
import com.kamegatze.code_generation.dto.auth.SwitchPassword;
import com.kamegatze.code_generation.entities.User;
import com.kamegatze.code_generation.services.AuthenticationService;
import com.kamegatze.code_generation.services.EmailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;


/**
 * General controller for registration and sign in system
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/service")
public class AuthController {

    private final AuthenticationService authenticationService;

    private final EmailServiceImpl emailService;


    /**
     * End point for registration users in system.
     * Accepts next json in body:
     * {
     *      "nickname":"nickname_user",
     *      "password":"password_user",
     *      "email":"email_user",
     *      "role":"select_role_in_system"
     * }
     * After return the same json object with links on different resources.
     * For example:
     * {
     *     "nickname":"nickname_user",
     *     "password":"password_user",
     *     "email":"email_user",
     *     "role":"select_role_in_system"
     *     "_links": {
     *         "self": {
     *             "href": "http://api/users/{nickname_user}"
     *         }
     *     }
     * }
     * */
    @PostMapping("/registration")
    public ResponseEntity<RegistrationDTO> handleRegistration(
           @Valid @RequestBody RegistrationDTO registration)
            throws NicknameExistException, EmailExistException, SQLException {

        registration = authenticationService.handleRegistration(registration);

        Link link = Link.of("http://api/users/" + registration.getNickname());

        registration.add(link);

        return ResponseEntity.ok(registration);
    }


   /**
     * End point for extradition jwt token after authentication and authorization in system
     * */
    @PostMapping("/sign_in")
    public ResponseEntity<JwtDto> handleSignIn(@RequestBody @Valid SignInDto sign) {

        JwtDto jwtDto = authenticationService.handleSignIn(sign);

        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/switch_password")
    public ResponseEntity<?> handleSwitchPassword(@RequestBody @Valid SwitchPassword switchPassword) {

        emailService.handlerSwitchPassword(switchPassword);

        return ResponseEntity.ok(Map.of("message",
                "request send email user: " + switchPassword.getLogin()));
    }
}
