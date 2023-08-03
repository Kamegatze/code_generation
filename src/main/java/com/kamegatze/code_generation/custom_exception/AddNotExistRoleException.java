package com.kamegatze.code_generation.custom_exception;

public class AddNotExistRoleException extends IllegalArgumentException{
    public AddNotExistRoleException(String message) {
        super(message);
    }

    public AddNotExistRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
