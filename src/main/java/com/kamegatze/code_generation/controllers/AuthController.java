package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.custom_exception.EmailExistException;
import com.kamegatze.code_generation.custom_exception.EnterCodeException;
import com.kamegatze.code_generation.custom_exception.NicknameExistException;
import com.kamegatze.code_generation.custom_exception.PasswordNotEqualsRetryPasswordException;
import com.kamegatze.code_generation.dto.auth.*;
import com.kamegatze.code_generation.dto.response.EResponse;
import com.kamegatze.code_generation.dto.response.Response;
import com.kamegatze.code_generation.entities.User;
import com.kamegatze.code_generation.services.AuthenticationService;
import com.kamegatze.code_generation.services.EmailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.*;


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
    public ResponseEntity<Response> handleRegistration(
            @Valid @RequestBody RegistrationDTO registration, UriComponentsBuilder uri)
            throws NicknameExistException, EmailExistException, SQLException {

        User user = authenticationService.handleRegistration(registration);

        Response response = Response.builder()
                .message("User " + user.getNickname() + " was created")
                .code(EResponse.RESPONSE_CREATED.getCode())
                .build();

        return ResponseEntity.created(
                uri.path("/api/user/{id}")
                        .build(Map.of("id", user.getId()))
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    /**
     * End point for extradition jwt token after authentication and authorization in system
     * */
    @PostMapping("/sign_in")
    public ResponseEntity<JwtDto> handleSignIn(@RequestBody @Valid SignInDto sign) {

        JwtDto jwtDto = authenticationService.handleSignIn(sign);

        return ResponseEntity.ok(jwtDto);
    }

    /**
     * End point for search user by login and generation temporary code for user
     * and remove this code via 5 minute
     * */
    @PostMapping("/switch_password")
    public ResponseEntity<Response> handleSwitchPassword(@RequestBody @Valid SwitchPassword switchPassword)
            throws UsernameNotFoundException, ExecutionException, InterruptedException {

       String code = emailService.handlerSwitchPassword(switchPassword);

       authenticationService.asyncRemoveCodeDelay(5, code);

       Response response = Response.builder()
                .message("Code for change the password was sent on your email")
                .code(EResponse.RESPONSE_SWITCH_PASSWORD.getCode())
                .build();

       return ResponseEntity.status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(response);
    }

    /**
     * End point for check code and remove this code
     * */
    @GetMapping("/check_code")
    public ResponseEntity<Response> handleCheckCode(@RequestParam String code) throws EnterCodeException {

        Boolean exist = emailService.handelCheckCode(code);

        Response response = Response.builder()
                .message("Temporary code was confirmed")
                .code(EResponse.RESPONSE_CONFIRMED_CODE.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    /**
     * End point for change password user.
     * Accept next json:
     * {
     *  newPassword:new password user,
     *  retryPassword: retry password user,
     *  login: login user
     * }
     * */
    @PostMapping("/change_password")
    public ResponseEntity<Response> handleChangePassword(@Valid @RequestBody ChangePassword changePassword)
            throws PasswordNotEqualsRetryPasswordException, EnterCodeException {
        authenticationService.handleChangePassword(changePassword);

        Response response = Response.builder()
                .message("Your password was change")
                .code(EResponse.RESPONSE_CHANGE_PASSWORD.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(response);
    }
}
