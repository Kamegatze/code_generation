package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.custom_exception.*;
import com.kamegatze.code_generation.dto.auth.*;
import com.kamegatze.code_generation.entities.ERole;
import com.kamegatze.code_generation.entities.Role;
import com.kamegatze.code_generation.entities.User;
import com.kamegatze.code_generation.jwt.JwtUtils;
import com.kamegatze.code_generation.repositories.RoleRepository;
import com.kamegatze.code_generation.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.concurrent.*;


/**
 *
 * Service for authentication and authorization users
 * and also extradition jwt token client
 * */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    /**
     * Method for find user on email or nickname
     * */
    public User searchUser(SwitchPassword switchPassword) throws UsernameNotFoundException {

        return userRepository.findByEmailOrNickname(switchPassword.getLogin(),
                        switchPassword.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("A user with this username was not found"));
    }

    /**
     * method for registration user in system
     * */
    @Transactional
    public User handleRegistration(RegistrationDTO registration)
            throws EmailExistException, NicknameExistException, SQLException, AddNotExistRoleException {

        if(userRepository.existsByNickname(registration.getNickname()))
            throw new NicknameExistException("This nickname is already taken. Please use other nickname.");

        if(userRepository.existsByEmail(registration.getEmail()))
            throw new EmailExistException("This email is already exist. Please use other email.");

        ERole eRole;

        try {
            eRole = ERole.valueOf(registration.getRole());
        } catch (IllegalArgumentException exception) {
            throw new AddNotExistRoleException("This role is not exist. Please select other role for user", exception);
        }

        Role role = roleRepository.findByRole(eRole)
                .orElseThrow(() -> new SQLException("Role " + registration.getRole() + " in system not exist"));

        User user = User.builder()
                .role(role)
                .email(registration.getEmail())
                .nickname(registration.getNickname())
                .password(passwordEncoder.encode(registration.getPassword()))
                .build();

        return userRepository.save(user);
    }


    /**
     * Method for sign in system user
     * */
    public JwtDto handleSignIn(SignInDto sign) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        sign.getLogin(),
                        sign.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generationJwtToken(authentication);

        JwtDto jwtDto = JwtDto.builder()
                .token(token)
                .build();

        return jwtDto;
    }

    @Transactional
    public void handleChangePassword(ChangePassword changePassword)
            throws PasswordNotEqualsRetryPasswordException, EnterCodeException {
        if(!changePassword.getNewPassword().equals(changePassword.getRetryPassword())) {
            throw new PasswordNotEqualsRetryPasswordException("New password not equals with retry password");
        }

        User user = userRepository.findBySwitchPasswordCode(changePassword.getCode())
                .orElseThrow(() -> new EnterCodeException("Login enter incorrect"));

        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));

        user.setSwitchPasswordCode("");

        userRepository.save(user);

    }

    @Transactional
    public void handeRemoveCode(String code) throws EnterCodeException {
        User user = userRepository.findBySwitchPasswordCode(code)
                .orElseThrow(() -> new EnterCodeException("code enter incorrect"));

        user.setSwitchPasswordCode("");

        userRepository.save(user);
    }

    public void asyncRemoveCodeDelay(Integer minute, String code) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> deleteCode = CompletableFuture.runAsync(() -> {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                try {
                    handeRemoveCode(code);
                } catch (EnterCodeException e) {
                    throw new RuntimeException(e);
                }
            }, minute, TimeUnit.MINUTES);
        });

        deleteCode.get();
    }
}
