package com.kamegatze.code_generation.advice;


import com.kamegatze.code_generation.controllers.AuthController;
import com.kamegatze.code_generation.custom_exception.AddNotExistRoleException;
import com.kamegatze.code_generation.custom_exception.EmailExistException;
import com.kamegatze.code_generation.custom_exception.NicknameExistException;
import com.kamegatze.code_generation.dto.auth.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthenticationAdvice {

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorMessage> handleEmailExistException(EmailExistException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(NicknameExistException.class)
    public ResponseEntity<ErrorMessage> handleNickNameExistException(NicknameExistException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(AddNotExistRoleException.class)
    public ResponseEntity<ErrorMessage> handleNotExistRoleException(AddNotExistRoleException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessage> handleSqlException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
