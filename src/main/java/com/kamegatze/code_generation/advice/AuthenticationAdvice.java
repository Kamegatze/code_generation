package com.kamegatze.code_generation.advice;


import com.kamegatze.code_generation.controllers.AuthController;
import com.kamegatze.code_generation.custom_exception.*;
import com.kamegatze.code_generation.dto.error.EError;
import com.kamegatze.code_generation.dto.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.Map;

@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthenticationAdvice {

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorMessage> handleEmailExistException(EmailExistException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .codeError(EError.ERROR_EMAIL_EXIST.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NicknameExistException.class)
    public ResponseEntity<ErrorMessage> handleNickNameExistException(NicknameExistException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .message(exception.getMessage())
                        .codeError(EError.ERROR_NICKNAME_EXIST.getCode())
                        .build());
    }

    @ExceptionHandler(AddNotExistRoleException.class)
    public ResponseEntity<ErrorMessage> handleNotExistRoleException(AddNotExistRoleException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .codeError(EError.ERROR_ROLE_NOT_EXIST.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessage> handleSqlException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .codeError(EError.ERROR_ROLE_NOT_EXIST.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleSwitchPassword(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ErrorMessage.builder()
                        .codeError(EError.ERROR_NICKNAME_NOT_FOUND.getCode())
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(EnterCodeException.class)
    public ResponseEntity<ErrorMessage> handleCheckCode(EnterCodeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .codeError(EError.ERROR_ENTRY_CODE.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(PasswordNotEqualsRetryPasswordException.class)
    public ResponseEntity<ErrorMessage> handleChangePassword(PasswordNotEqualsRetryPasswordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .codeError(EError.ERROR_PASSWORD_EQUALS_RETRY_PASSWORD.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidReject(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .codeError(EError.ERROR_VALIDATION_EXCEPTION.getCode())
                        .message(exception.getMessage())
                        .build());
    }
}
