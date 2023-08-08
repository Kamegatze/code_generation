package com.kamegatze.code_generation.controllers;

import com.kamegatze.code_generation.custom_exception.EmailExistException;
import com.kamegatze.code_generation.custom_exception.EnterCodeException;
import com.kamegatze.code_generation.custom_exception.NicknameExistException;
import com.kamegatze.code_generation.custom_exception.PasswordNotEqualsRetryPasswordException;
import com.kamegatze.code_generation.dto.auth.*;
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

    /**
     * End point for search user by login and generation temporary code for user
     * and remove this code via 5 minute
     * */
    @PostMapping("/switch_password")
    public ResponseEntity<?> handleSwitchPassword(@RequestBody @Valid SwitchPassword switchPassword)
            throws UsernameNotFoundException, ExecutionException, InterruptedException {

       String code = emailService.handlerSwitchPassword(switchPassword);

       authenticationService.asyncRemoveCodeDelay(5, code);

       return ResponseEntity.ok(Map.of("exist",
               true, "message", "user exist"));
    }

    /**
     * End point for check code and remove this code
     * */
    @GetMapping("/check_code")
    public ResponseEntity<?> handleCheckCode(@RequestParam String code) throws EnterCodeException {

        Boolean exist = emailService.handelCheckCode(code);

        return ResponseEntity.ok(Map.of("exist", exist,
                "message", "user exist"));
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
    public ResponseEntity<?> handleChangePassword(@Valid @RequestBody ChangePassword changePassword)
            throws PasswordNotEqualsRetryPasswordException, EnterCodeException {
       authenticationService.handleChangePassword(changePassword);

       return ResponseEntity.status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(Map.of("message", "Your password change"));
    }
}
