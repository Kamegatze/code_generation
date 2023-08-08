package com.kamegatze.code_generation.custom_exception;

public class PasswordNotEqualsRetryPasswordException extends Exception{
    public PasswordNotEqualsRetryPasswordException(String message) {
        super(message);
    }
}
