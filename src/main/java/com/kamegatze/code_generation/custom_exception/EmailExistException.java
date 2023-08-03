package com.kamegatze.code_generation.custom_exception;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
